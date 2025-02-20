package com.Harevich.rideservice.dto.queue;

import java.util.UUID;

public record PassengerDriverRideQueuePair(

        PassengerDriverQueueItemIdPair queueItemsPair,

        UUID passengerId,

        UUID driverId,

        String from,

        String to

) {
}
