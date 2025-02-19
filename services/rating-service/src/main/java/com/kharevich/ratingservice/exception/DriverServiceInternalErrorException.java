package com.kharevich.ratingservice.exception;

public class DriverServiceInternalErrorException extends RuntimeException {
    public DriverServiceInternalErrorException(String message) {
        super(message);
    }
}
