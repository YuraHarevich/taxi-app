package com.Harevich.passengerservice.util.mapper;

import com.Harevich.passengerservice.dto.PassengerResponse;
import com.Harevich.passengerservice.dto.UserResponse;
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
    UserResponse toUserResponse(PassengerResponse passengerResponse);

}
