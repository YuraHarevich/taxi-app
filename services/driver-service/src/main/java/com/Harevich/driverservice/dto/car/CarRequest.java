package com.Harevich.driverservice.dto.car;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CarRequest(
        @NotBlank(message = "color is mandatory")
        @Schema(description = "Cars color", example = "red")
        String color,
        @NotBlank(message = "number is mandatory")
        @Schema(description = "Cars number", example = "7777BB-7")
        String number,
        @NotBlank(message = "name is mandatory")
        @Schema(description = "Cars brand", example = "BMW")
        String brand
) {
}
