package com.kharevich.ratingservice.sideservices.ride;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kharevich.ratingservice.sideservices.ride.enumerations.RideStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kharevich.ratingservice.util.mapper.desializer.DurationDeserializer;
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

        @Schema(description = "drivers id")
        UUID driverId,

        @Schema(description = "Ride status", example = "ACCEPTED")
        RideStatus rideStatus,

        @Schema(description = "Ride time", example = "12:30")
        @JsonDeserialize(using = DurationDeserializer.class)
        Duration rideTime

) {

    @JsonProperty("rideTime")
    public String getRideTime() {
        if(rideTime == null)
            return null;
        long minutes = rideTime.toMinutes();
        long seconds = rideTime.toSecondsPart();
        return String.format("%02d:%02d", minutes, seconds);
    }

}

