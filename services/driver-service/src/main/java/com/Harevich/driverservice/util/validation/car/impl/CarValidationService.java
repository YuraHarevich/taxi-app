package com.Harevich.driverservice.util.validation.car.impl;

import com.Harevich.driverservice.exception.CarIsAlreadyOccupiedException;
import com.Harevich.driverservice.exception.RepeatedDataException;
import com.Harevich.driverservice.model.Car;
import com.Harevich.driverservice.repository.CarRepository;
import com.Harevich.driverservice.util.validation.car.CarValidation;
import com.Harevich.driverservice.util.constants.DriverServiceResponseConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarValidationService implements CarValidation {
    private final CarRepository carRepository;
    @Override
    public void alreadyExistsByNumber(String number) {
        if(carRepository.existsByNumber(number))
            throw new RepeatedDataException(DriverServiceResponseConstants.REPEATED_CAR_NUMBER);
    }

    @Override
    public Car findIfExistsByNumber(String number) {
        return carRepository.findByNumber(number)
                .orElseThrow(()->new RepeatedDataException(DriverServiceResponseConstants.CAR_NOT_FOUND));
    }

    @Override
    public Car findIfExistsById(UUID id) {
        return carRepository.findById(id)
                .orElseThrow(()->new RepeatedDataException(DriverServiceResponseConstants.CAR_NOT_FOUND));
    }


    @Override
    public void isDeleted(UUID id) {
        carRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(()->new EntityNotFoundException(DriverServiceResponseConstants.CAR_DELETED));
    }

    @Override
    public void carIsAlreadyOccupied(UUID id) {
        Car car = carRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(DriverServiceResponseConstants.CAR_NOT_FOUND));
        if(car.getDriver() != null)
            throw new CarIsAlreadyOccupiedException(DriverServiceResponseConstants.CAR_IS_OCCUPIED);
    }
}
