package com.Harevich.driverservice.util.mapper;

import com.Harevich.driverservice.dto.response.DriverResponse;
import com.Harevich.driverservice.dto.response.UserResponse;
import com.Harevich.driverservice.model.Driver;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserMapper {

    @Mapping(source = "id", target = "externalId")
    @Mapping(source = "name", target = "firstname")
    @Mapping(source = "surname", target = "lastname")
    UserResponse toUserResponse(DriverResponse driverResponse);

}
