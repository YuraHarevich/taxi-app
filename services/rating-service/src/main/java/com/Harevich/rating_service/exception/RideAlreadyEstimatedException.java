package com.Harevich.rating_service.exception;

public class RideAlreadyEstimatedException extends RuntimeException {
    public RideAlreadyEstimatedException(String message) {
        super(message);
    }
}
