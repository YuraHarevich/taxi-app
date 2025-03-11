package com.Harevich.driverservice.util.validation.car.impl;

import com.Harevich.driverservice.exception.CarIsAlreadyOccupiedException;
import com.Harevich.driverservice.exception.RepeatedDataException;
import com.Harevich.driverservice.model.Car;
import com.Harevich.driverservice.model.Driver;
import com.Harevich.driverservice.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarValidationUnitTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarValidationService carValidationService;

    private final UUID carId = UUID.randomUUID();
    private final String carNumber = "ABC123";
    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setId(carId);
        car.setNumber(carNumber);
        car.setDeleted(false);
    }

    @Test
    void alreadyExistsByNumber_ShouldThrowException_WhenNumberExists() {
        when(carRepository.existsByNumber(carNumber)).thenReturn(true);

        assertThrows(RepeatedDataException.class,
                () -> carValidationService.alreadyExistsByNumber(carNumber));
    }

    @Test
    void alreadyExistsByNumber_ShouldNotThrowException_WhenNumberNotExists() {
        when(carRepository.existsByNumber(carNumber)).thenReturn(false);

        assertDoesNotThrow(() -> carValidationService.alreadyExistsByNumber(carNumber));
    }

    @Test
    void findIfExistsByNumber_ShouldReturnCar_WhenCarExists() {
        when(carRepository.findByNumber(carNumber)).thenReturn(Optional.of(car));

        Car result = carValidationService.findIfExistsByNumber(carNumber);

        assertEquals(car, result);
    }

    @Test
    void findIfExistsByNumber_ShouldThrowException_WhenCarNotFound() {
        when(carRepository.findByNumber(carNumber)).thenReturn(Optional.empty());

        assertThrows(RepeatedDataException.class,
                () -> carValidationService.findIfExistsByNumber(carNumber));
    }

    @Test
    void findIfExistsById_ShouldReturnCar_WhenCarExists() {
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        Car result = carValidationService.findIfExistsById(carId);

        assertEquals(car, result);
    }

    @Test
    void findIfExistsById_ShouldThrowException_WhenCarNotFound() {
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(RepeatedDataException.class,
                () -> carValidationService.findIfExistsById(carId));
    }

    @Test
    void isDeleted_ShouldThrowException_WhenCarIsDeleted() {
        when(carRepository.findByIdAndDeletedFalse(carId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> carValidationService.isDeleted(carId));
    }

    @Test
    void isDeleted_ShouldNotThrowException_WhenCarIsNotDeleted() {
        when(carRepository.findByIdAndDeletedFalse(carId)).thenReturn(Optional.of(car));

        assertDoesNotThrow(() -> carValidationService.isDeleted(carId));
    }

    @Test
    void carIsAlreadyOccupied_ShouldThrowException_WhenCarHasDriver() {
        Driver driver = new Driver();
        driver.setId(UUID.randomUUID());
        car.setDriver(driver);

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        assertThrows(CarIsAlreadyOccupiedException.class,
                () -> carValidationService.carIsAlreadyOccupied(carId));
    }

    @Test
    void carIsAlreadyOccupied_ShouldNotThrowException_WhenCarHasNoDriver() {
        car.setDriver(null);

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        assertDoesNotThrow(() -> carValidationService.carIsAlreadyOccupied(carId));
    }
}

