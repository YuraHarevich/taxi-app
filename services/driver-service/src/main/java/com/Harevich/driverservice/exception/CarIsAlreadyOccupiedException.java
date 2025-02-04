package com.Harevich.driverservice.exception;

public class CarIsAlreadyOccupiedException extends RuntimeException {
    public CarIsAlreadyOccupiedException(String message) {
        super(message);
    }
}
