package com.Harevich.driverservice.service.impl;

import com.Harevich.driverservice.dto.request.CarRequest;
import com.Harevich.driverservice.dto.response.CarResponse;
import com.Harevich.driverservice.dto.response.PageableResponse;
import com.Harevich.driverservice.model.Car;
import com.Harevich.driverservice.repository.CarRepository;
import com.Harevich.driverservice.service.CarService;
import com.Harevich.driverservice.util.check.car.CarValidation;
import com.Harevich.driverservice.util.constants.RegularExpressionConstants;
import com.Harevich.driverservice.util.mapper.CarMapper;
import com.Harevich.driverservice.util.mapper.PageMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final CarValidation carValidation;
    private final PageMapper pageMapper;

    @Override
    public CarResponse createNewCar(@Valid CarRequest request) {
       carValidation.alreadyExistsByNumber(request.number());
       return carMapper.toResponse(
               carRepository.save(carMapper.toCar(request)));
    }

    @Override
    public CarResponse updateCar(@Valid CarRequest request, UUID id) {
        Car car = carValidation.findIfExistsById(id);
        carValidation.alreadyExistsByNumber(request.number());
        carValidation.isDeleted(id);
        carMapper.changeCarByRequest(request,car);
        Car updatedCar = carRepository.saveAndFlush(car);
        return carMapper.toResponse(updatedCar);
    }

    @Override
    public CarResponse getCarById(UUID id) {
        Car car = carValidation.findIfExistsById(id);
        carValidation.isDeleted(id);
        return carMapper.toResponse(car);
    }

    @Override
    public CarResponse getCarByNumber(@Pattern(regexp = RegularExpressionConstants.CAR_NUMBER_REGEX) String number) {
        Car car = carValidation.findIfExistsByNumber(number);
        carValidation.isDeleted(car.getId());
        return carMapper.toResponse(car);
    }

    @Override
    @Transactional
    public void deleteCarById(UUID id) {
        Car car = carValidation.findIfExistsById(id);
        carValidation.isDeleted(id);
        car.setDeleted(true);
        if(!Objects.equals(car.getDriver(),null))
            car.getDriver().setDeleted(true);
        carRepository.save(car);
    }

    @Override
    public PageableResponse<CarResponse> getAllAvailableCars(int pageNumber, int size) {
        return pageMapper
                .toResponse(carRepository.findByDriverIsNullAndDeletedFalse(PageRequest.of(pageNumber,size)));
    }
}
