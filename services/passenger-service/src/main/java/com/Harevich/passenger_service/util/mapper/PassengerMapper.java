package com.Harevich.passenger_service.util.mapper;

import com.Harevich.passenger_service.dto.PassengerRequest;
import com.Harevich.passenger_service.dto.PassengerResponse;
import com.Harevich.passenger_service.model.Passenger;

public class PassengerMapper {
    public static Passenger toPassenger(PassengerRequest request) {
        return Passenger.builder()
                .name(request.name())
                .surname(request.surname())
                .email(request.email())
                .number(request.number())
                .build();
    }

    public static PassengerResponse toResponse(Passenger passenger) {
        return new PassengerResponse(
                passenger.getId(),
                passenger.getName(),
                passenger.getSurname(),
                passenger.getEmail(),
                passenger.getNumber()
        );
    }
}

