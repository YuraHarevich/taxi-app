package com.Harevich.ratingservice.exception;

public class RideAlreadyEstimatedException extends RuntimeException {
    public RideAlreadyEstimatedException(String message) {
        super(message);
    }
}
