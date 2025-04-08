package ru.kharevich.authenticationservice.exceptions;

public class ExternalValidationException extends RuntimeException {
    public ExternalValidationException(String message) {
        super(message);
    }
}
