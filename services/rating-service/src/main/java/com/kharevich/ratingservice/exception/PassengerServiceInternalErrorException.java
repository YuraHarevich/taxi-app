package com.kharevich.ratingservice.exception;

public class PassengerServiceInternalErrorException extends RuntimeException {
  public PassengerServiceInternalErrorException(String message) {
    super(message);
  }
}
