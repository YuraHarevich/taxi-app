package ru.kharevich.authenticationservice.exceptions;

public class JwtConverterException extends RuntimeException {

  public JwtConverterException(Throwable cause) {
    super(cause);
  }

  public JwtConverterException(String message) {
    super(message);
  }

}
