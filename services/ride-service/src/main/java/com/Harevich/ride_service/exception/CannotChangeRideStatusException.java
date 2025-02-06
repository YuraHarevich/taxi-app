package com.Harevich.ride_service.exception;

public class CannotChangeRideStatusException extends RuntimeException {

    public CannotChangeRideStatusException(String message) {
        super(message);
    }

}
