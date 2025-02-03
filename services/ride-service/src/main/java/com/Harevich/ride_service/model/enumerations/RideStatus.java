package com.Harevich.ride_service.model.enumerations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum RideStatus {
    CREATED(0),
    ACCEPTED(1),
    DECLINED(2),
    ON_THE_WAY(3),
    FINISHED(4);

    private final int sexCode;

    public static Optional<Sex> fromCode(int code) {
        return Arrays.stream(RideStatus.values())
                .filter(sex -> sex.sexCode == code)
                .findAny();
    }
}
