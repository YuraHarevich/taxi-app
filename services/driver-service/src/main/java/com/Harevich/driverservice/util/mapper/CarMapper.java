package com.Harevich.driverservice.util.mapper;

import com.Harevich.driverservice.dto.car.CarRequest;
import com.Harevich.driverservice.dto.car.CarResponse;
import com.Harevich.driverservice.dto.driver.DriverRequest;
import com.Harevich.driverservice.dto.driver.DriverResponse;
import com.Harevich.driverservice.model.Car;
import com.Harevich.driverservice.model.Driver;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CarMapper {
    CarResponse toResponse(Car car);

    CarRequest toRequest(CarResponse carResponse);

    Car toCar(CarRequest carRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void changeCarByRequest(DriverRequest driverRequest, @MappingTarget Car car);

}