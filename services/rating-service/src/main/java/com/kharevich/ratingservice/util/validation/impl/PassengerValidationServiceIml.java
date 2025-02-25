package com.kharevich.ratingservice.util.validation.impl;

import com.kharevich.ratingservice.client.PassengerServiceClient;
import com.kharevich.ratingservice.util.validation.PersonValidationService;
import lombok.RequiredArgsConstructor;
import com.kharevich.ratingservice.util.validation.PersonValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PassengerValidationServiceIml implements PersonValidationService {

    private final PassengerServiceClient passengerServiceClient;

    @Override
    public void checkIfPersonExists(UUID id) {
        log.info("PassengerValidationServiceIml.sending request to passenger service");
        passengerServiceClient.getPassengerIfExists(id);
    }

}
