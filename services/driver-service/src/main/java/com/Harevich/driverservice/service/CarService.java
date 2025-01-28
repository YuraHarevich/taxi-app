package com.Harevich.driverservice.service;

import com.Harevich.driverservice.dto.car.CarRequest;
import com.Harevich.driverservice.dto.car.CarResponse;
import jakarta.validation.Valid;

import java.util.UUID;

public interface CarService {
    CarResponse createNewCar(@Valid CarRequest request, UUID driverId);

    CarResponse updateCar(@Valid CarRequest request, UUID id);

    CarResponse getById(UUID id);

    CarResponse getByNumber(String number);

    void deleteById(UUID id);
}
