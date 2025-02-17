package com.kharevich.rideservice.exception;

public class CannotChangeRideStatusException extends RuntimeException {

    public CannotChangeRideStatusException(String message) {
        super(message);
    }

}
