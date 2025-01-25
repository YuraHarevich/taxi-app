package com.Harevich.driverservice.exception;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class UniqueException extends RuntimeException{
    private final String message;
}
