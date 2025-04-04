package ru.kharevich.authenticationservice.dto.request;

import jakarta.validation.constraints.Email;

import java.util.UUID;

public record ExternalPersonRequest(

        UUID id,

        String firstname,

        String lastname,

        @Email
        String email

) {
}
