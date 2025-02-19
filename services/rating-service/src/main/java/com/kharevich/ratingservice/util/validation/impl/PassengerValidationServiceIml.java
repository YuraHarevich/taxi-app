package com.kharevich.ratingservice.util.validation.impl;

import com.kharevich.ratingservice.client.PassengerServiceClient;
import com.kharevich.ratingservice.util.validation.PersonValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassengerValidationServiceIml implements PersonValidationService {

    private final PassengerServiceClient passengerServiceClient;

    @Override
    public void checkIfPersonExists(UUID id) {
        passengerServiceClient.getPassengerIfExists(id);
    }

}
