package com.Harevich.rideservice.service.impl;

import com.Harevich.rideservice.dto.QueuePairForMakingUpRide;
import com.Harevich.rideservice.dto.request.RideRequest;
import com.Harevich.rideservice.model.queue.DriverQueueElement;
import com.Harevich.rideservice.model.queue.PassengerQueueElement;
import com.Harevich.rideservice.repository.DriverQueueRepository;
import com.Harevich.rideservice.repository.PassengerQueueRepository;
import com.Harevich.rideservice.service.DriverQueueService;
import com.Harevich.rideservice.service.PassengerQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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

    public Optional<QueuePairForMakingUpRide> pickPair(){
        var driverOpt = driverQueueRepository.findFirstByIsProceedFalseOrderByCreatedAtAsc();
        var passengerOpt = passengerQueueRepository.findFirstByIsProceedFalseOrderByCreatedAtAsc();

        QueuePairForMakingUpRide queuePair = null;
        if(driverOpt.isPresent() && passengerOpt.isPresent()) {
            queuePair = new QueuePairForMakingUpRide(
                    passengerOpt.get().getPassengerId(),
                    driverOpt.get().getDriverId(),
                    passengerOpt.get().getFrom(),
                    passengerOpt.get().getTo());

            driverOpt.get().setIsProceed(true);
            driverOpt.get().setProceedAt(LocalDateTime.now());
            passengerOpt.get().setIsProceed(true);
            passengerOpt.get().setProceedAt(LocalDateTime.now());

            driverQueueRepository.save(driverOpt.get());
            passengerQueueRepository.save(passengerOpt.get());
        }
        return Optional.ofNullable(queuePair);
    }

}
