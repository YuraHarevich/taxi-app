package com.Harevich.passengerservice.exceptions;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class PassengersDataRepeatException extends RuntimeException{
    private final String message;
}
