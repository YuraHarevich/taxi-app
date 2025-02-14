package com.Harevich.rideservice.exception;

public class DriverIsBusyException extends RuntimeException {
    public DriverIsBusyException(String message) {
        super(message);
    }
}
