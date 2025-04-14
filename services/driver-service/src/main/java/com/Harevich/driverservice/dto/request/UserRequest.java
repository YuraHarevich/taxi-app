package com.Harevich.driverservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequest(

        @NotBlank(message = "name is mandatory")
        String name,

        @NotBlank(message = "surname is mandatory")
        String surname,

        @Email(message = "email should be valid")
        String email,

        @Pattern(regexp = "\\+375(29|44|25|33|17)\\d{7}", message = "Invalid phone number format")
        String number

) {
}
