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
    @Mapping(source = "cars", target = "car_id", qualifiedByName = "mapFirstCarId")
    DriverResponse toResponse(Driver driver);

    Driver toDriver(DriverRequest driverRequest);

    DriverRequest toRequest(DriverResponse driverResponse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void changeDriverByRequest(DriverRequest driverRequest, @MappingTarget Driver driver);

    @Named("mapFirstCarId")
    default UUID mapFirstCarId(List<Car> cars) {
        return (cars != null && !cars.isEmpty()) ? cars.get(0).getId() : null;
    }
}