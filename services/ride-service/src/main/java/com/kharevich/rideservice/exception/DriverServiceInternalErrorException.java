package com.kharevich.rideservice.exception;

public class DriverServiceInternalErrorException extends RuntimeException {
    public DriverServiceInternalErrorException(String message) {
        super(message);
    }
}
