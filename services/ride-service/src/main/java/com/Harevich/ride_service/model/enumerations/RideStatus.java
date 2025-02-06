package com.Harevich.ride_service.model.enumerations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum RideStatus {
    CREATED(0),
    ACCEPTED(100),
    DECLINED(200),
    ON_THE_WAY(300),
    FINISHED(400);

    private final int rideCode;

    public static Optional<RideStatus> fromCode(int code) {
        return Arrays.stream(RideStatus.values())
                .filter(ride -> ride.rideCode == code)
                .findAny();
    }
}
