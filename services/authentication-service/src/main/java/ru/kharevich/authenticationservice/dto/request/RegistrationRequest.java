package ru.kharevich.authenticationservice.dto.request;

import jakarta.validation.constraints.Email;

public record RegistrationRequest(

        String username,

        String firstname,

        String lastname,

        @Email
        String email,

        String password

) {
}
