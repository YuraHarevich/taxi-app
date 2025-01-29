package com.Harevich.driverservice.util.check.car;

import com.Harevich.driverservice.model.Car;

import java.util.UUID;

public interface CarValidation {
    void alreadyExistsByNumber(String number);
    Car findIfExistsByNumber(String number);
    Car findIfExistsById(UUID id);
    void isDeleted(UUID id);
    void carIsAlreadyOccupied(UUID id);
}
