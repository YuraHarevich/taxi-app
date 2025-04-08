package ru.kharevich.authenticationservice.exceptions;


public class RepeatedDataException extends RuntimeException{
    public RepeatedDataException(String message) {
        super(message);
    }
}
