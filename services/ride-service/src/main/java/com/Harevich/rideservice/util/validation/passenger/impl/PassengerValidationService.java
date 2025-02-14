package com.Harevich.rideservice.util.validation.passenger.impl;

import com.Harevich.rideservice.client.PassengerServiceClient;
import com.Harevich.rideservice.util.validation.passenger.PassengerValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.Harevich.rideservice.util.constants.RideServiceResponseConstants.PASSENGER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PassengerValidationService implements PassengerValidation {

    private final PassengerServiceClient passengerClient;

    @Override
    public void throwExceptionIfPassengerDoesNotExist(UUID passengerId) {
        passengerClient.getPassengerIfExists(passengerId);
    }

}
