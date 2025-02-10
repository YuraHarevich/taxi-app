package com.Harevich.ride_service.exception;

public class DriverIsBusyException extends RuntimeException {
    public DriverIsBusyException(String message) {
        super(message);
    }
}
