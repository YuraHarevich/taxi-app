package com.Harevich.driverservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record CarResponse(
        UUID id,
        @Schema(description = "Cars color", example = "red")
        String color,
        @Schema(description = "Cars number", example = "7877 MX-6")
        String number,
        @Schema(description = "Cars brand", example = "BMW")
        String brand,
        @Schema(description = "Cars owner")
        UUID driverId
) {
}
