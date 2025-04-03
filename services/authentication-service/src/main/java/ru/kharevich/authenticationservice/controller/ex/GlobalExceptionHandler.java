package ru.kharevich.authenticationservice.controller.ex;

import jakarta.ws.rs.NotAuthorizedException;
import org.keycloak.common.VerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kharevich.authenticationservice.dto.ErrorMessage;
import ru.kharevich.authenticationservice.exceptions.JwtConverterException;
import ru.kharevich.authenticationservice.exceptions.RepeatedUserData;
import ru.kharevich.authenticationservice.exceptions.UserCreationException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            VerificationException.class
    })
    public ResponseEntity<ErrorMessage> handleVerification(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorMessage.builder()
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({
            JwtConverterException.class
    })
    public ResponseEntity<ErrorMessage> handleJWT(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.builder()
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({
            RepeatedUserData.class
    })
    public ResponseEntity<ErrorMessage> handleRepeatedData(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorMessage.builder()
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({
            UserCreationException.class
    })
    public ResponseEntity<ErrorMessage> handleUserCreation(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorMessage.builder()
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({
            NotAuthorizedException.class
    })
    public ResponseEntity<ErrorMessage> handleNotAuthorized(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorMessage.builder()
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

}
