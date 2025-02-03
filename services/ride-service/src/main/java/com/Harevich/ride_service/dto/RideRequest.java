package com.Harevich.ride_service.dto;

import java.util.UUID;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RideRequest {

    @NotBlank(message = "start point is mandatory")
    @Schema(description = "Ride start address", example = "Мендзялеева 13")
    private String from;

    @NotBlank(message = "finish point is mandatory")
    @Schema(description = "Ride finish address", example = "Таёжная 19")
    private String to;

}
