package com.Harevich.driverservice.controller.ex;

import com.Harevich.driverservice.dto.ErrorMessage;
import com.Harevich.driverservice.exception.CarIsAlreadyOccupiedException;
import com.Harevich.driverservice.exception.RepeatedDataException;
import com.Harevich.driverservice.exception.SexConvertionException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            EntityNotFoundException.class
    })
    public ResponseEntity<ErrorMessage> handle(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorMessage.builder()
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({
            RepeatedDataException.class,
            CarIsAlreadyOccupiedException.class,
            SexConvertionException.class
    })
    public ResponseEntity<ErrorMessage> handle(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
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
            ConstraintViolationException.class
    })
    public ResponseEntity<ErrorMessage> handleConstraintViolationExceptions(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.builder()
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

}