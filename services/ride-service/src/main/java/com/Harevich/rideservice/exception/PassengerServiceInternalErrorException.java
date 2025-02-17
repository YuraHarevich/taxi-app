package com.Harevich.rideservice.exception;

public class PassengerServiceInternalErrorException extends RuntimeException {
    public PassengerServiceInternalErrorException(String message) {
        super(message);
    }
}
