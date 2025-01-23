package com.Harevich.passenger_service.dto;

import com.Harevich.passenger_service.util.constants.RegularConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;

import java.util.Objects;
import java.util.UUID;

public record PassengerResponse(UUID id,
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
