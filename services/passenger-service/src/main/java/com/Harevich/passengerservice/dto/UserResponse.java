package com.Harevich.passengerservice.dto;

import jakarta.validation.constraints.Email;

import java.util.UUID;

public record UserResponse(

        UUID externalId,

        String firstname,

        String lastname,

        @Email(message = "email should be valid")
        String email

) {
}
