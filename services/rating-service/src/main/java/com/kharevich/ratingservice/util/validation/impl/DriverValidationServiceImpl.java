package com.kharevich.ratingservice.util.validation.impl;

import com.kharevich.ratingservice.client.DriverServiceClient;
import com.kharevich.ratingservice.util.validation.PersonValidationService;
import lombok.RequiredArgsConstructor;
import com.kharevich.ratingservice.util.validation.PersonValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverValidationServiceImpl implements PersonValidationService {

    private final DriverServiceClient driverServiceClient;

    @Override
    public void checkIfPersonExists(UUID id) {
        log.info("DriverValidationServiceIml.sending request to driver service");
        driverServiceClient.getDriverIfExists(id);
    }

}
