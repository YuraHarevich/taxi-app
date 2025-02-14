package com.Harevich.rideservice.util.validation.driver.impl;

import com.Harevich.rideservice.client.DriverServiceClient;
import com.Harevich.rideservice.util.validation.driver.DriverValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassengerValidationService implements DriverValidation {

    private final DriverServiceClient driverClient;

    @Override
    public void throwExceptionIfPassengerDoesNotExist(UUID passengerId) {
        driverClient.getPassengerIfExists(passengerId);
    }

}