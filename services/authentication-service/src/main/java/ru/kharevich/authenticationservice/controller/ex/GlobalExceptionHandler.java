package ru.kharevich.authenticationservice.controller.ex;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotAuthorizedException;
import org.keycloak.common.VerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kharevich.authenticationservice.dto.ErrorMessage;
import ru.kharevich.authenticationservice.exceptions.ClientRightException;
import ru.kharevich.authenticationservice.exceptions.DecoderException;
import ru.kharevich.authenticationservice.exceptions.ExternalValidationException;
import ru.kharevich.authenticationservice.exceptions.JwtConverterException;
import ru.kharevich.authenticationservice.exceptions.RepeatedDataException;
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
            DecoderException.class
    })
    public ResponseEntity<ErrorMessage> handleServiceUnavailable(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ErrorMessage.builder()
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({
            EntityNotFoundException.class
    })
    public ResponseEntity<ErrorMessage> handleNoFound(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorMessage.builder()
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({
            JwtConverterException.class,
            ExternalValidationException.class,
    })
    public ResponseEntity<ErrorMessage> handleBadRequest(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.builder()
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var error = ex.getBindingResult().getAllErrors().getFirst();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.builder()
                        .message(error.getDefaultMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({
            RepeatedUserData.class,
            RepeatedDataException.class,
            UserCreationException.class
    })
    public ResponseEntity<ErrorMessage> handleConflict(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorMessage.builder()
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({
            ClientRightException.class
    })
    public ResponseEntity<ErrorMessage> handleForbidden(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
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

    @ExceptionHandler({
            RuntimeException.class
    })
    public ResponseEntity<ErrorMessage> handleUnresolvedExceptions(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ErrorMessage.builder()
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

}
