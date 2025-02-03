package com.Harevich.ride_service.dto;

import com.Harevich.ride_service.model.enumerations.RideStatus;

import java.math.BigDecimal;
import java.util.UUID;

public class RideResponse {
    private UUID id;
    @Schema(description = "Ride start address", example = "Мендзялеева 13")
    private String from;
    @Schema(description = "Ride finish address", example = "Таёжная 19")
    private String to;
    @Schema(description = "Ride price", example = "20.10")
    private BigDecimal price;
    @Schema(description = "passengers id")
    private UUID passengerId;
    @Schema(description = "passengers id")
    private UUID driverId;
    @Schema(description = "Ride status", example = "ACCEPTED")
    private RideStatus rideStatus;
}
