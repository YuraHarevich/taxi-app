package com.Harevich.passengerservice.dto;

import com.Harevich.passengerservice.util.constants.RegularConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record PassengerRequest(
        @NotBlank(message = "name is mandatory")
        @Schema(description = "Passengers name", example = "Maksim")
        String name,
        @NotBlank(message = "surname is mandatory")
        @Schema(description = "Passengers surname", example = "Komissarov")
        String surname,
        @Email(message = "email should be valid")
        @Schema(description = "Passengers email", example = "mymail@gmail.com")
        String email,
        @Pattern(regexp = RegularConstants.PHONE_NUMBER_REGEX, message = "Invalid phone number format")
        @Schema(description = "Passengers phone number", example = "+375332567899")
        String number
) {
}
