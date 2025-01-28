package com.Harevich.driverservice.exception;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class RepeatedDriverDataException extends RuntimeException{
    private final String message;
}
