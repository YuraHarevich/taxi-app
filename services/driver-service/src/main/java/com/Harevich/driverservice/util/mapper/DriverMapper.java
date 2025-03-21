package com.Harevich.driverservice.util.mapper;

import com.Harevich.driverservice.dto.request.DriverRequest;
import com.Harevich.driverservice.dto.response.DriverResponse;
import com.Harevich.driverservice.model.Driver;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface DriverMapper {

    @Mapping(source = "car.id", target = "carId")
    DriverResponse toResponse(Driver driver);

    Driver toDriver(DriverRequest driverRequest);

    DriverRequest toRequest(DriverResponse driverResponse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void changeDriverByRequest(DriverRequest driverRequest, @MappingTarget Driver driver);

}