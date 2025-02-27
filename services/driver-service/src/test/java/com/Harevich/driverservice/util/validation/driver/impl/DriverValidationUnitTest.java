package com.Harevich.driverservice.util.validation.driver.impl;

import com.Harevich.driverservice.exception.RepeatedDataException;
import com.Harevich.driverservice.model.Car;
import com.Harevich.driverservice.model.Driver;
import com.Harevich.driverservice.repository.DriverRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DriverValidationUnitTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverValidationService driverValidationService;

    private final UUID driverId = UUID.randomUUID();
    private final String email = "test@example.com";
    private final String phoneNumber = "1234567890";
    private Driver driver;
    private Car car;

    @BeforeEach
    void setUp() {
        driver = new Driver();
        driver.setId(driverId);
        driver.setEmail(email);
        driver.setNumber(phoneNumber);
        driver.setDeleted(false);

        car = new Car();
        car.setId(UUID.randomUUID());
    }

    @Test
    void alreadyExistsByEmail_ShouldThrowException_WhenEmailExists() {
        when(driverRepository.existsByEmail(email)).thenReturn(true);

        assertThrows(RepeatedDataException.class,
                () -> driverValidationService.alreadyExistsByEmail(email));
    }

    @Test
    void alreadyExistsByEmail_ShouldNotThrowException_WhenEmailNotExists() {
        when(driverRepository.existsByEmail(email)).thenReturn(false);

        assertDoesNotThrow(() -> driverValidationService.alreadyExistsByEmail(email));
    }

    @Test
    void alreadyExistsByNumber_ShouldThrowException_WhenPhoneNumberExists() {
        when(driverRepository.existsByNumber(phoneNumber)).thenReturn(true);

        assertThrows(RepeatedDataException.class,
                () -> driverValidationService.alreadyExistsByNumber(phoneNumber));
    }

    @Test
    void alreadyExistsByNumber_ShouldNotThrowException_WhenPhoneNumberNotExists() {
        when(driverRepository.existsByNumber(phoneNumber)).thenReturn(false);

        assertDoesNotThrow(() -> driverValidationService.alreadyExistsByNumber(phoneNumber));
    }

    @Test
    void findIfExistsById_ShouldReturnDriver_WhenDriverExists() {
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        Driver result = driverValidationService.findIfExistsById(driverId);

        assertEquals(driver, result);
    }

    @Test
    void findIfExistsById_ShouldThrowException_WhenDriverNotFound() {
        when(driverRepository.findById(driverId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> driverValidationService.findIfExistsById(driverId));
    }

    @Test
    void isDeleted_ShouldThrowException_WhenDriverIsDeleted() {
        when(driverRepository.findByIdAndDeletedFalse(driverId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> driverValidationService.isDeleted(driverId));
    }

    @Test
    void isDeleted_ShouldNotThrowException_WhenDriverIsNotDeleted() {
        when(driverRepository.findByIdAndDeletedFalse(driverId)).thenReturn(Optional.of(driver));

        assertDoesNotThrow(() -> driverValidationService.isDeleted(driverId));
    }

    @Test
    void carToChangeIsTheSameAsPrevious_ShouldReturnTrue_WhenCarIsSame() {
        driver.setCar(car);

        boolean result = driverValidationService.carToChangeIsTheSameAsPrevious(driver, car);

        assertTrue(result);
    }

    @Test
    void carToChangeIsTheSameAsPrevious_ShouldReturnFalse_WhenDriverHasNoCar() {
        driver.setCar(null);

        boolean result = driverValidationService.carToChangeIsTheSameAsPrevious(driver, car);

        assertFalse(result);
    }

    @Test
    void carToChangeIsTheSameAsPrevious_ShouldReturnFalse_WhenCarIsDifferent() {
        driver.setCar(new Car());

        boolean result = driverValidationService.carToChangeIsTheSameAsPrevious(driver, car);

        assertFalse(result);
    }
}

