package com.Harevich.rideservice.dto;

import java.util.UUID;

public record QueuePairForMakingUpRide(

        UUID passengerId,

        UUID driverId,

        String from,

        String to

) {
}
