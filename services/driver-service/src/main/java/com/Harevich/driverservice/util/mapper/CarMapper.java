package com.Harevich.driverservice.util.mapper;

import com.Harevich.driverservice.dto.request.CarRequest;
import com.Harevich.driverservice.dto.response.CarResponse;
import com.Harevich.driverservice.model.Car;
import org.hibernate.query.Page;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CarMapper {
    @Mapping(source = "driver.id", target = "driverId")
    CarResponse toResponse(Car car);

    CarRequest toRequest(CarResponse carResponse);

    Car toCar(CarRequest carRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void changeCarByRequest(CarRequest carRequest, @MappingTarget Car car);

}