package com.Harevich.ride_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


public record RideRequest (
    @NotBlank(message = "start point is mandatory")
    @Schema(description = "Ride start address", example = "Мендзялеева 13")
    String from,

    @NotBlank(message = "finish point is mandatory")
    @Schema(description = "Ride finish address", example = "Таёжная 19")
    String to
){
}
