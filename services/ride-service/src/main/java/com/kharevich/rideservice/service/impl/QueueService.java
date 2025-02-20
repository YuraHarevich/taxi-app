package com.kharevich.rideservice.service.impl;

import com.kharevich.rideservice.dto.queue.PassengerDriverQueueItemIdPair;
import com.kharevich.rideservice.dto.queue.PassengerDriverRideQueuePair;
import com.kharevich.rideservice.dto.request.RideRequest;
import com.kharevich.rideservice.model.DriverQueueElement;
import com.kharevich.rideservice.model.PassengerQueueElement;
import com.kharevich.rideservice.repository.DriverQueueRepository;
import com.kharevich.rideservice.repository.PassengerQueueRepository;
import com.kharevich.rideservice.service.DriverQueueService;
import com.kharevich.rideservice.service.PassengerQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.kharevich.rideservice.model.enumerations.ProcessingStatus.IN_PROCESS;
import static com.kharevich.rideservice.model.enumerations.ProcessingStatus.NOT_PROCESSED;
import static com.kharevich.rideservice.model.enumerations.ProcessingStatus.PROCESSED;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueService implements PassengerQueueService, DriverQueueService {

    private final PassengerQueueRepository passengerQueueRepository;

    private final DriverQueueRepository driverQueueRepository;

    @Override
    public void addDriver(UUID driverId) {
        DriverQueueElement driverQueueElement = DriverQueueElement.builder()
                .driverId(driverId)
                .build();
        driverQueueRepository.save(driverQueueElement);
    }

    @Override
    public void addPassenger(RideRequest request) {
        PassengerQueueElement passengerQueueElement = PassengerQueueElement.builder()
                .passengerId(request.passengerId())
                .to(request.to())
                .from(request.from())
                .build();
        passengerQueueRepository.save(passengerQueueElement);
    }

    @Transactional
    public Optional<PassengerDriverRideQueuePair> pickPair(){
        var driverOpt = driverQueueRepository.findFirstByProcessingStatusOrderByCreatedAtAsc(NOT_PROCESSED);
        var passengerOpt = passengerQueueRepository.findFirstByProcessingStatusOrderByCreatedAtAsc(NOT_PROCESSED);

        PassengerDriverRideQueuePair queuePair = null;

        if(driverOpt.isPresent() && passengerOpt.isPresent()) {
            DriverQueueElement driverQueueElement = driverOpt.get();
            PassengerQueueElement passengerQueueElement = passengerOpt.get();

            PassengerDriverQueueItemIdPair idsPair = new PassengerDriverQueueItemIdPair(
                    passengerQueueElement.getId(),
                    driverQueueElement.getId()
            );

            queuePair = new PassengerDriverRideQueuePair(
                    idsPair,
                    passengerQueueElement.getPassengerId(),
                    driverQueueElement.getDriverId(),
                    passengerQueueElement.getFrom(),
                    passengerQueueElement.getTo()
            );

            driverQueueElement.setProcessingStatus(IN_PROCESS);
            passengerQueueElement.setProcessingStatus(IN_PROCESS);

            driverQueueRepository.save(driverQueueElement);
            passengerQueueRepository.save(passengerQueueElement);
        }
        return Optional.ofNullable(queuePair);
    }

    public void markAsProcessed(PassengerDriverRideQueuePair passengerDriverRideQueuePair) {
        var passengerOpt = passengerQueueRepository.findById(passengerDriverRideQueuePair.queueItemsPair().passengerQueueItemId());
        if(passengerOpt.isPresent()){
            passengerOpt.get().setProcessingStatus(PROCESSED);
            passengerQueueRepository.save(passengerOpt.get());
        } else {
            log.info("Couldn't mark as processed passenger {}",passengerDriverRideQueuePair.passengerId());
        }
        var driverOpt = driverQueueRepository.findById(passengerDriverRideQueuePair.queueItemsPair().driverQueueItemId());
        if(driverOpt.isPresent()){
            driverOpt.get().setProcessingStatus(PROCESSED);
            driverQueueRepository.save(driverOpt.get());
        } else {
            log.info("Couldn't mark as processed driver {}",passengerDriverRideQueuePair.driverId());
        }
    }
}
