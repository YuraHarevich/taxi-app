package com.Harevich.driverservice.util.validation.driver.impl;

import com.Harevich.driverservice.exception.RepeatedDataException;
import com.Harevich.driverservice.model.Car;
import com.Harevich.driverservice.model.Driver;
import com.Harevich.driverservice.repository.DriverRepository;
import com.Harevich.driverservice.util.validation.driver.DriverValidation;
import com.Harevich.driverservice.util.constants.DriverServiceResponseConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverValidationService implements DriverValidation {

    private final DriverRepository driverRepository;

    @Override
    public void alreadyExistsByEmail(String email) {
        if(driverRepository.existsByEmail(email))
            throw new RepeatedDataException(DriverServiceResponseConstants.REPEATED_EMAIL);
    }

    @Override
    public void alreadyExistsByNumber(String number) {
        if(driverRepository.existsByNumber(number))
            throw new RepeatedDataException(DriverServiceResponseConstants.REPEATED_PHONE_NUMBER);
    }

    @Override
    public Driver findIfExistsById(UUID id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DriverServiceResponseConstants.DRIVER_NOT_FOUND));
    }

    @Override
    public void isDeleted(UUID id) {
        driverRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(DriverServiceResponseConstants.DRIVER_DELETED));
    }

    @Override
    public boolean carToChangeIsTheSameAsPrevious(Driver driver, Car car) {
        if(Objects.equals(driver.getCar(),null))
            return false;
        return Objects.equals(driver.getCar().getId(),car.getId());

    }

}
