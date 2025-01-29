package com.Harevich.driverservice.util.mapper;

import com.Harevich.driverservice.dto.driver.DriverRequest;
import com.Harevich.driverservice.dto.driver.DriverResponse;
import com.Harevich.driverservice.model.Car;
import com.Harevich.driverservice.model.Driver;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface DriverMapper {
    @Mapping(source = "car.id", target = "car_id")
    DriverResponse toResponse(Driver driver);

    Driver toDriver(DriverRequest driverRequest);

    DriverRequest toRequest(DriverResponse driverResponse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void changeDriverByRequest(DriverRequest driverRequest, @MappingTarget Driver driver);
}