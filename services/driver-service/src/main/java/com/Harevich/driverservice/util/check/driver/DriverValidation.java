package com.Harevich.driverservice.util.check.driver;

import com.Harevich.driverservice.model.Car;
import com.Harevich.driverservice.model.Driver;

import java.util.UUID;

public interface DriverValidation {
    void alreadyExistsByEmail(String email);
    void alreadyExistsByNumber(String number);
    Driver findIfExistsById(UUID id);
    void isDeleted(UUID id);
    boolean carToChangeIsTheSameAsPrevious(Driver driver, Car car);
}
