package com.kharevich.ratingservice.util.validation.impl;

import com.kharevich.ratingservice.client.DriverServiceClient;
import com.kharevich.ratingservice.util.validation.PersonValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverValidationServiceIml implements PersonValidationService {

    private final DriverServiceClient driverServiceClient;

    @Override
    public void checkIfPersonExists(UUID id) {
        driverServiceClient.getDriverIfExists(id);
    }

}
