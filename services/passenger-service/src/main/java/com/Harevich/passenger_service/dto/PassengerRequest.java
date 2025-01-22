package com.Harevich.passenger_service.dto;

import jakarta.validation.constraints.*;

public record PassengerRequest(
        @NotBlank(message = "name is mandatory")
        String name,
        @NotBlank(message = "surname is mandatory")
        String surname,
        @Email(message = "email should be valid")
        String email,
        @Pattern(regexp = "\\+375(29|44|25|33|17)\\d{7}", message = "Invalid phone number format")
        String number
) {
}
