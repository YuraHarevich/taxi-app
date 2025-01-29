package com.Harevich.driverservice.dto.request;

import com.Harevich.driverservice.util.constants.RegularExpressionConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DriverRequest(
        @NotBlank(message = "name is mandatory")
        @Schema(description = "Drivers name", example = "Tom")
        String name,
        @NotBlank(message = "surname is mandatory")
        @Schema(description = "Drivers surname", example = "Hanks")
        String surname,
        @Email(message = "email should be valid")
        @Schema(description = "Drivers email", example = "1mymail@gmail.com")
        String email,
        @Pattern(regexp = RegularExpressionConstants.PHONE_NUMBER_REGEX, message = "Invalid phone number format")
        String number
) {
}
