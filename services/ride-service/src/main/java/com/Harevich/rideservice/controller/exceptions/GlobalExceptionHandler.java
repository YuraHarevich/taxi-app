package com.Harevich.rideservice.controller.exceptions;

import com.Harevich.rideservice.dto.ErrorMessage;
import com.Harevich.rideservice.exception.AddressNotFoundException;
import com.Harevich.rideservice.exception.CannotChangeRideStatusException;
import com.Harevich.rideservice.exception.DriverIsBusyException;
import com.Harevich.rideservice.exception.GeolocationServiceBadRequestException;
import com.Harevich.rideservice.exception.GeolocationServiceUnavailableException;
import com.Harevich.rideservice.exception.PassengerServiceInternalError;
import com.Harevich.rideservice.exception.RideStatusConvertionException;
import com.Harevich.rideservice.exception.UpdateNotAllowedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static com.Harevich.rideservice.util.constants.RideServiceResponseConstants.UNEXPECTED_SERVER_ERROR;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler({
            EntityNotFoundException.class
    })
    public ResponseEntity<ErrorMessage> handle(EntityNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorMessage.builder()
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
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
            RideStatusConvertionException.class,
            CannotChangeRideStatusException.class
    })
    public ResponseEntity<ErrorMessage> handleValidationExceptions(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorMessage.builder()
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({
            ConstraintViolationException.class,
            AddressNotFoundException.class,
            GeolocationServiceBadRequestException.class,
            UpdateNotAllowedException.class,
            DriverIsBusyException.class
    })
    public ResponseEntity<ErrorMessage> handleExceptions(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.builder()
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({
            GeolocationServiceUnavailableException.class,
            PassengerServiceInternalError.class
    })
    public ResponseEntity<ErrorMessage> handleExceptions(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ErrorMessage.builder()
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({
            Exception.class
    })
    public ResponseEntity<ErrorMessage> handleUnpredictableExceptions(Exception ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorMessage.builder()
                        .message(UNEXPECTED_SERVER_ERROR)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

}
