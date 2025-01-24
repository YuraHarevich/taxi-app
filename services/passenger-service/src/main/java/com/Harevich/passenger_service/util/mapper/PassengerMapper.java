package com.Harevich.passenger_service.util.mapper;

import com.Harevich.passenger_service.dto.PassengerRequest;
import com.Harevich.passenger_service.dto.PassengerResponse;
import com.Harevich.passenger_service.model.Passenger;
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

