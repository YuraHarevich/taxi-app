package ru.kharevich.authenticationservice.dto.response;

import jakarta.validation.constraints.Email;

import java.util.UUID;

public record UserResponse(

        UUID externalId,

        String firstname,

        String lastname,

        String email

) {
}
