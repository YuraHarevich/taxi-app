package com.kharevich.ratingservice.util.validation.impl;

import com.kharevich.ratingservice.client.passenger.PassengerServiceClient;
import com.kharevich.ratingservice.util.validation.PersonValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PassengerValidationServiceImpl implements PersonValidationService {

    private final PassengerServiceClient passengerServiceClient;

    @Override
    public void checkIfPersonExists(UUID id) {
        log.info("PassengerValidationServiceIml.sending request to passenger service");
        passengerServiceClient.getPassengerIfExists(id);
    }

}
