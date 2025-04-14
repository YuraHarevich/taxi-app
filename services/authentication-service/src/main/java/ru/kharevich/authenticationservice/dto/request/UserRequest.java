package ru.kharevich.authenticationservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record UserRequest(

        @NotBlank(message = "name is mandatory")
        String name,

        @NotBlank(message = "surname is mandatory")
        String surname,

        @Email(message = "invalid email format")
        String email,

        @Pattern(regexp = "\\+375(29|44|25|33|17)\\d{7}", message = "Invalid phone number format")
        String number

) {
}
