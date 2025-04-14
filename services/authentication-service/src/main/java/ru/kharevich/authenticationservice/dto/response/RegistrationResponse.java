package ru.kharevich.authenticationservice.dto.response;

import java.util.UUID;

public record RegistrationResponse(

    UUID id,

    String username,

    String firstname,

    String lastname,

    String email

) {
}
