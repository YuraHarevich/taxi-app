package com.Harevich.rideservice.service.impl;

import com.Harevich.rideservice.dto.request.DriverQueueRequest;
import com.Harevich.rideservice.dto.request.RideRequest;
import com.Harevich.rideservice.model.queue.DriverQueueElement;
import com.Harevich.rideservice.model.queue.PassengerQueueElement;
import com.Harevich.rideservice.repository.DriverQueueRepository;
import com.Harevich.rideservice.repository.PassengerQueueRepository;
import com.Harevich.rideservice.service.DriverQueueService;
import com.Harevich.rideservice.service.PassengerQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<RideRequest> pickPassenger(){
        var passengerOpt = passengerQueueRepository.findFirstByIsProceedFalseOrderByCreatedAtAsc();
        RideRequest request = null;
        if(passengerOpt.isPresent()){
            request = new RideRequest(
                    passengerOpt.get().getFrom(),
                    passengerOpt.get().getTo(),
                    passengerOpt.get().getPassengerId());
        }
        return Optional.ofNullable(request);
    }

    @Override
    public Optional<DriverQueueRequest> pickDriver(){
        var driverOpt = driverQueueRepository.findFirstByIsProceedFalseOrderByCreatedAtAsc();
        DriverQueueRequest request = null;
        if(driverOpt.isPresent()){
            request = new DriverQueueRequest(driverOpt.get().getDriverId());
        }
        return Optional.ofNullable(request);
    }

}
