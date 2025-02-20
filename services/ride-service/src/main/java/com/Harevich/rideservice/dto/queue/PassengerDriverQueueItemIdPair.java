package com.Harevich.rideservice.dto.queue;

import java.util.UUID;

public record PassengerDriverQueueItemIdPair(

        UUID passengerQueueItemId,

        UUID driverQueueItemId

) {
}
