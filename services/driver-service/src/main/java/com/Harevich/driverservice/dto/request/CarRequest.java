package com.Harevich.driverservice.dto.request;

import com.Harevich.driverservice.util.constants.RegularExpressionConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CarRequest(
        @NotBlank(message = "color is mandatory")
        @Schema(description = "Cars color", example = "red")
        String color,
        @NotBlank(message = "number is mandatory")
        @Schema(description = "Cars number", example = "7777 BB-7")
        @Pattern(regexp = RegularExpressionConstants.CAR_NUMBER_REGEX, message = "Invalid car number format")
        String number,
        @NotBlank(message = "name is mandatory")
        @Schema(description = "Cars brand", example = "BMW")
        String brand
) {
}
