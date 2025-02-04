package com.Harevich.ride_service.exception;

public class GeolocationServiceUnavailableException extends RuntimeException {
    public GeolocationServiceUnavailableException(String message) {
        super(message);
    }
}
