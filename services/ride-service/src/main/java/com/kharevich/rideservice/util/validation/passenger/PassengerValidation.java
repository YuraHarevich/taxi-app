package com.kharevich.rideservice.util.validation.passenger;

import java.util.UUID;

public interface PassengerValidation {
    void throwExceptionIfPassengerDoesNotExist(UUID passengerId);
}
