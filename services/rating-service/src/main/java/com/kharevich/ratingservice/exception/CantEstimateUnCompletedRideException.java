package com.kharevich.ratingservice.exception;

public class CantEstimateUnCompletedRideException extends RuntimeException {
    public CantEstimateUnCompletedRideException(String message) {
        super(message);
    }
}
