package ru.kharevich.authenticationservice.exceptions;

public class ExternalServiceUnavailableException extends RuntimeException {
  public ExternalServiceUnavailableException(String message) {
    super(message);
  }
}
