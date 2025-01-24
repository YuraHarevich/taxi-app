package com.Harevich.driverservice.mapper;

import com.Harevich.driverservice.dto.CarRequest;
import com.Harevich.driverservice.dto.CarResponse;
import com.Harevich.driverservice.model.Car;

public class CarMapper {
    public static Car toCar(CarRequest carRequest) {
        return Car.builder()
                .color(carRequest.color())
                .brand(carRequest.brand())
                .number(carRequest.number())
                .build();
    }

    public static CarResponse toCarRequest(Car car) {
        return new CarResponse(
                car.getId(),
                car.getColor(),
                car.getNumber(),
                car.getBrand()
        );
    }
}
