package com.Harevich.driverservice.dto.response.auth;

import java.util.UUID;

public record RegistrationResponse(

    UUID id,

    String username,

    String firstname,

    String lastname,

    String email

) {
}
