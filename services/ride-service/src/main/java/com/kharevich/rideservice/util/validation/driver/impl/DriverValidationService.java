package com.kharevich.rideservice.util.validation.driver.impl;

import com.kharevich.rideservice.client.DriverServiceClient;
import com.kharevich.rideservice.util.validation.driver.DriverValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverValidationService implements DriverValidation {

    private final DriverServiceClient driverClient;

    @Override
    public void throwExceptionIfDriverDoesNotExist(UUID driverId) {
        driverClient.getDriverIfExists(driverId);
    }

}