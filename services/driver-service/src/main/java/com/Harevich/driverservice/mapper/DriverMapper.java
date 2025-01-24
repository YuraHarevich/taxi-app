package com.Harevich.driverservice.mapper;

import com.Harevich.driverservice.dto.DriverRequest;
import com.Harevich.driverservice.dto.DriverResponse;
import com.Harevich.driverservice.model.Driver;

public class DriverMapper {
    public static Driver toDriver(DriverRequest request) {
        return Driver.builder()
                .name(request.name())
                .surname(request.name())
                .email(request.email())
                .car(CarMapper.toCar(request.car()))
                .build();
    }

    public static DriverResponse toDriverResponse(Driver driver) {
        return new DriverResponse(
                driver.getId(),
                driver.getName(),
                driver.getSurname(),
                driver.getEmail(),
                driver.getRate(),
                CarMapper.toCarRequest(driver.getCar())
        );
    }
}
