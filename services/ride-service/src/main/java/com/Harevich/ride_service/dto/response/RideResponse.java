package com.Harevich.ride_service.dto.response;

import com.Harevich.ride_service.model.enumerations.RideStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

public record RideResponse(
    UUID id,
    @Schema(description = "Ride start address", example = "Мендзялеева 13")
    String from,
    @Schema(description = "Ride finish address", example = "Таёжная 19")
    String to,
    @Schema(description = "Ride price", example = "20.10")
    BigDecimal price,
    @Schema(description = "passengers id")
    UUID passengerId,
    @Schema(description = "passengers id")
    UUID driverId,
    @Schema(description = "Ride status", example = "ACCEPTED")
    RideStatus rideStatus,
    @Schema(description = "Ride time", example = "12:30")
    Duration rideTime
){
}
