package com.Harevich.rideservice.service.impl;

import com.Harevich.rideservice.dto.request.RideRequest;
import com.Harevich.rideservice.model.queue.PassengerQueueElement;
import com.Harevich.rideservice.repository.PassengerQueueRepository;
import com.Harevich.rideservice.service.PassengerQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassengerQueueServiceImpl implements PassengerQueueService {

    private final PassengerQueueRepository repository;


    @Override
    public void addElement(UUID passengerId, RideRequest request) {
        PassengerQueueElement passengerQueueElement = PassengerQueueElement.builder()
                .passengerId(passengerId)
                .to(request.to())
                .from(request.from())
                .build();
        repository.save(passengerQueueElement);
    }

    @Override
    public PassengerQueueElement getElement() {
        return null;
    }
}
