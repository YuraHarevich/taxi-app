package ru.kharevich.authenticationservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static ru.kharevich.authenticationservice.utils.constants.AuthServiceConstantValues.PHONE_NUMBER_REGULAR_EXPRESSION;

public record RegistrationRequest(

        @NotBlank(message = "username is mandatory")
        String username,

        @NotBlank(message = "firstname is mandatory")
        String firstname,

        @NotBlank(message = "lastname is mandatory")
        String lastname,

        @Email(message = "Invalid email format")
        String email,

        @Pattern(regexp = PHONE_NUMBER_REGULAR_EXPRESSION, message = "Invalid phone number format")
        String number,

        @NotBlank(message = "password is mandatory")
        String password

) {
}
