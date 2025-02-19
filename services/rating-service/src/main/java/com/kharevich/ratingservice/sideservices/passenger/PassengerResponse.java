package com.kharevich.ratingservice.sideservices.passenger;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record PassengerResponse(
        UUID id,
        @Schema(description = "Passengers name", example = "Maksim")
        String name,
        @Schema(description = "Passengers surname", example = "Komissarov")
        String surname,
        @Schema(description = "Passengers email", example = "mymail@gmail.com")
        String email,
        @Schema(description = "Passengers phone number", example = "+375332567899")
        String number
) {
}
