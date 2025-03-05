package com.kharevich.rideservice.util.validation.driver.impl;

import com.kharevich.rideservice.client.DriverServiceClient;
import com.kharevich.rideservice.util.validation.driver.DriverValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverValidationService implements DriverValidation {

    private final DriverServiceClient driverClient;

    @Override
    public void throwExceptionIfDriverDoesNotExist(UUID driverId) {
        log.info("DriverValidationServiceIml.sending request to driver service");
        driverClient.getDriverIfExists(driverId);
    }

}