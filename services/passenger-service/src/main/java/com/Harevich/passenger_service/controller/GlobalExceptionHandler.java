package com.Harevich.passenger_service.controller;
import com.Harevich.passenger_service.dto.ErrorMessage;
import com.Harevich.passenger_service.exceptions.UniqueException;
import com.Harevich.passenger_service.util.constants.PassengerServiceResponseConstants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handle(EntityNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorMessage.builder()
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(UniqueException.class)
    public ResponseEntity<ErrorMessage> handle(UniqueException exception) {
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
    //обработка на случай дубликата поля при попытке изменения

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Pattern pattern = Pattern.compile("Подробности: Key \\((.*?)\\)=\\((.*?)\\)");
        Matcher matcher = pattern.matcher(ex.getMessage());
        String message = null;
        if (matcher.find()) {
            String field = matcher.group(1);
            String value = matcher.group(2);
            message = field.equals("email")? PassengerServiceResponseConstants.REPEATED_EMAIL: PassengerServiceResponseConstants.REPEATED_PHONE_NUMBER + "("+value+")";
        }
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorMessage.builder()
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
