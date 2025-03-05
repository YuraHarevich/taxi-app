package com.Harevich.passengerservice.util.check.impl;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.Harevich.passengerservice.exceptions.PassengersDataRepeatException;
import com.Harevich.passengerservice.model.Passenger;
import com.Harevich.passengerservice.repository.PassengerRepository;
import com.Harevich.passengerservice.util.constants.PassengerValidationConstants;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class PassengerValidationUnitTest {

    @Mock
    private PassengerRepository repository;

    @InjectMocks
    private PassengerValidationService service;

    private UUID passengerId;

    @BeforeEach
    void setUp() {
        passengerId = UUID.randomUUID();
    }

    @Test
    void alreadyExistsByEmail_ShouldThrowException_WhenEmailExists() {
        String email = "test@example.com";
        when(repository.existsByEmail(email)).thenReturn(true);

        PassengersDataRepeatException exception = assertThrows(
                PassengersDataRepeatException.class,
                () -> service.alreadyExistsByEmail(email)
        );
        assertEquals(PassengerValidationConstants.REPEATED_EMAIL, exception.getMessage());
    }

    @Test
    void alreadyExistsByEmail_ShouldNotThrowException_WhenEmailDoesNotExist() {
        String email = "test@example.com";
        when(repository.existsByEmail(email)).thenReturn(false);

        assertDoesNotThrow(() -> service.alreadyExistsByEmail(email));
    }

    @Test
    void alreadyExistsByNumber_ShouldThrowException_WhenNumberExists() {
        String number = "1234567890";
        when(repository.existsByNumber(number)).thenReturn(true);

        PassengersDataRepeatException exception = assertThrows(
                PassengersDataRepeatException.class,
                () -> service.alreadyExistsByNumber(number)
        );
        assertEquals(PassengerValidationConstants.REPEATED_PHONE_NUMBER, exception.getMessage());
    }

    @Test
    void alreadyExistsByNumber_ShouldNotThrowException_WhenNumberDoesNotExist() {
        String number = "1234567890";
        when(repository.existsByNumber(number)).thenReturn(false);

        assertDoesNotThrow(() -> service.alreadyExistsByNumber(number));
    }

    @Test
    void existsById_ShouldThrowException_WhenIdDoesNotExist() {
        when(repository.existsById(passengerId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> service.existsById(passengerId)
        );
        assertEquals(PassengerValidationConstants.PASSENGER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void existsById_ShouldNotThrowException_WhenIdExists() {
        when(repository.existsById(passengerId)).thenReturn(true);

        assertDoesNotThrow(() -> service.existsById(passengerId));
    }

    @Test
    void isDeleted_ShouldThrowException_WhenPassengerIsDeleted() {
        when(repository.findByIdAndDeletedFalse(passengerId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> service.isDeleted(passengerId)
        );
        assertEquals(PassengerValidationConstants.PASSENGER_DELETED, exception.getMessage());
    }

    @Test
    void isDeleted_ShouldNotThrowException_WhenPassengerIsNotDeleted() {
        when(repository.findByIdAndDeletedFalse(passengerId)).thenReturn(Optional.of(mock(Passenger.class)));

        assertDoesNotThrow(() -> service.isDeleted(passengerId));
    }
}
