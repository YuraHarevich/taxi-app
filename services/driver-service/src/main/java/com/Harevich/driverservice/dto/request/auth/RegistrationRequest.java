package com.Harevich.driverservice.dto.request.auth;

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
