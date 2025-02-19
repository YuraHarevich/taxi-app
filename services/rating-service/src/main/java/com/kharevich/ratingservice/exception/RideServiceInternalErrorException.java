package com.kharevich.ratingservice.exception;

public class RideServiceInternalErrorException extends RuntimeException {
    public RideServiceInternalErrorException(String message) {
        super(message);
    }
}
