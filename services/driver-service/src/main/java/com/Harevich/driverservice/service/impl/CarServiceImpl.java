package com.Harevich.driverservice.service.impl;

import com.Harevich.driverservice.dto.car.CarRequest;
import com.Harevich.driverservice.dto.car.CarResponse;
import com.Harevich.driverservice.repository.CarRepository;
import com.Harevich.driverservice.service.CarService;
import com.Harevich.driverservice.util.check.car.CarValidation;
import com.Harevich.driverservice.util.mapper.CarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final CarValidation carValidation;

    @Override
    public CarResponse createNewCar(CarRequest request, UUID driverId) {
       //carValidation.alreadyExistsByNumber(request.number());
        return null;
    }

    @Override
    public CarResponse updateCar(CarRequest request, UUID id) {
        return null;
    }

    @Override
    public CarResponse getById(UUID id) {
        return null;
    }

    @Override
    public CarResponse getByNumber(String number) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }
}
