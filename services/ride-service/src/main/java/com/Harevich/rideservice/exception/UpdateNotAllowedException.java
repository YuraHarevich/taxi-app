package com.Harevich.rideservice.exception;

public class UpdateNotAllowedException extends RuntimeException {
    public UpdateNotAllowedException(String message) {
        super(message);
    }
}
