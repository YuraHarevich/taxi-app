package com.kharevich.rideservice.util.validation.passenger.impl;

import com.kharevich.rideservice.client.PassengerServiceClient;
import com.kharevich.rideservice.util.validation.passenger.PassengerValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassengerValidationService implements PassengerValidation {

    private final PassengerServiceClient passengerClient;

    @Override
    public void throwExceptionIfPassengerDoesNotExist(UUID passengerId) {
        passengerClient.getPassengerIfExists(passengerId);
    }

}
