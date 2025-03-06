package com.kharevich.rideservice.util.validation.passenger.impl;

import com.kharevich.rideservice.client.passenger.PassengerServiceClient;
import com.kharevich.rideservice.util.validation.passenger.PassengerValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PassengerValidationService implements PassengerValidation {

    private final PassengerServiceClient passengerClient;

    @Override
    public void throwExceptionIfPassengerDoesNotExist(UUID passengerId) {
        log.info("PassengerValidationService.sending request to passenger service");
        passengerClient.getPassengerIfExists(passengerId);
    }

}
