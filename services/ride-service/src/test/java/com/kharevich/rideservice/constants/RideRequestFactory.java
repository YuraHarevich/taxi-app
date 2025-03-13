package com.kharevich.rideservice.constants;

import com.kharevich.rideservice.dto.request.RideRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideRequestFactory {

    public static RideRequest createDefaultRequest() {
        return new RideRequest(
                "Мендзялеева 12",
                "Сталетава 10",
                UUID.fromString("110e8400-e29b-41d4-a716-446655440000")
        );
    }

}
