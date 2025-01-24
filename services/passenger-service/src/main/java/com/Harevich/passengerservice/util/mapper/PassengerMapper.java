package com.Harevich.passengerservice.util.mapper;

import com.Harevich.passengerservice.dto.PassengerRequest;
import com.Harevich.passengerservice.dto.PassengerResponse;
import com.Harevich.passengerservice.model.Passenger;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PassengerMapper {

    PassengerResponse toResponse(Passenger passenger);
    PassengerRequest toRequest(PassengerResponse passenger);

    Passenger toPassenger(PassengerRequest passengerRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void changePassengerByRequest(PassengerRequest passengerRequest, @MappingTarget Passenger passenger);

}

