package com.Harevich.driverservice.service.impl;

import com.Harevich.driverservice.dto.request.DriverRequest;
import com.Harevich.driverservice.dto.response.DriverResponse;
import com.Harevich.driverservice.model.Car;
import com.Harevich.driverservice.model.Driver;
import com.Harevich.driverservice.repository.CarRepository;
import com.Harevich.driverservice.repository.DriverRepository;
import com.Harevich.driverservice.service.DriverService;
import com.Harevich.driverservice.util.validation.car.CarValidation;
import com.Harevich.driverservice.util.validation.driver.DriverValidation;
import com.Harevich.driverservice.util.mapper.DriverMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    private final CarRepository carRepository;

    private final DriverMapper driverMapper;

    private final DriverValidation driverValidation;

    private final CarValidation carValidation;

    @Override
    public DriverResponse createNewDriver(DriverRequest request) {
        driverValidation.alreadyExistsByEmail(request.email());
        driverValidation.alreadyExistsByNumber(request.number());
        Driver driver = driverMapper.toDriver(request);
        return driverMapper.toResponse(
                driverRepository.save(driver));
    }

    @Override
    @Transactional
    public DriverResponse updateDriver(DriverRequest request, UUID id) {
        Driver driver = driverValidation.findIfExistsById(id);
        driverValidation.alreadyExistsByEmail(request.email());
        driverValidation.alreadyExistsByNumber(request.number());
        driverValidation.isDeleted(id);
        driverMapper.changeDriverByRequest(request,driver);
        Driver updatedDriver = driverRepository.saveAndFlush(driver);
        return driverMapper.toResponse(updatedDriver);
    }

    @Override
    public DriverResponse getById(UUID id) {
        Driver driver = driverValidation.findIfExistsById(id);
        driverValidation.isDeleted(id);
        return driverMapper.toResponse(driver);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        Driver driver = driverValidation.findIfExistsById(id);
        driverValidation.isDeleted(id);

        driver.setDeleted(true);
        Car car = driver.getCar();

        if(Objects.equals(car,null)){
            car.setDriver(null);
        }
        driver.setCar(null);
        driverRepository.save(driver);
    }

    @Override
    @Transactional
    public DriverResponse assignPersonalCar(UUID driverId, UUID carId) {
        Driver driver = driverValidation.findIfExistsById(driverId);
        driverValidation.isDeleted(driverId);

        Car car = carValidation.findIfExistsById(carId);
        carValidation.isDeleted(carId);

        if(driverValidation.carToChangeIsTheSameAsPrevious(driver,car))
            return driverMapper.toResponse(driver);

        carValidation.carIsAlreadyOccupied(carId);
        Car pastCar = driver.getCar();
            if (pastCar != null) {
                pastCar.setDriver(null);
                carRepository.save(pastCar);
            }
        driver.setCar(car);
        car.setDriver(driver);
        Driver updatedDriver = driverRepository.saveAndFlush(driver);
        return driverMapper.toResponse(updatedDriver);
    }

}
