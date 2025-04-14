package ru.kharevich.authenticationservice.exceptions;

public class RepeatedUserData extends RuntimeException {
    public RepeatedUserData(String message) {
        super(message);
    }
}
