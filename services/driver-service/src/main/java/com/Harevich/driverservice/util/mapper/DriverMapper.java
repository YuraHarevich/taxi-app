package com.Harevich.driverservice.util.mapper;

import com.Harevich.driverservice.dto.driver.DriverRequest;
import com.Harevich.driverservice.dto.driver.DriverResponse;
import com.Harevich.driverservice.model.Driver;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface DriverMapper {
    @Mapping(source = "driver.id", target = "driverId")
    DriverResponse toResponse(Driver passenger);

    DriverRequest toRequest(DriverResponse passenger);

    Driver toDriver(DriverRequest driverRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void changePassengerByRequest(DriverRequest driverRequest, @MappingTarget Driver driver);

}