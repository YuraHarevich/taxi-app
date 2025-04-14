package ru.kharevich.authenticationservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(

        @NotBlank(message = "username is mandatory")
        String username,

        @NotBlank(message = "password is mandatory")
        String password

) {
}
