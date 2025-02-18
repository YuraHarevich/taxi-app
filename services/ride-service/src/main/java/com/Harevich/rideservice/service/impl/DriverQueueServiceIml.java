package com.Harevich.rideservice.service.impl;

import com.Harevich.rideservice.model.queue.DriverQueueElement;
import com.Harevich.rideservice.repository.DriverQueueRepository;
import com.Harevich.rideservice.service.DriverQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverQueueServiceIml implements DriverQueueService {

    private final DriverQueueRepository repository;


    @Override
    public void addElement(UUID driverId) {
        DriverQueueElement driverQueueElement = DriverQueueElement.builder()
                .driverId(driverId)
                .build();
        repository.save(driverQueueElement);
    }

    @Override
    public DriverQueueElement getElement() {
        return null;
    }
}
