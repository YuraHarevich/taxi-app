package com.Harevich.passengerservice.exceptions;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

public class PassengersDataRepeatException extends RuntimeException{
    public PassengersDataRepeatException(String message) {
        super(message);
    }
}
