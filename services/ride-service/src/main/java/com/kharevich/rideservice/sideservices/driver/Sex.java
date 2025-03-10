package com.kharevich.rideservice.sideservices.driver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum Sex {
    MALE(1),
    FEMALE(2),
    NON_BINARY(3),
    OTHERS(0);

    private final int sexCode;

    public static Optional<Sex> fromCode(int code) {
        return Arrays.stream(Sex.values())
                .filter(sex -> sex.sexCode == code)
                .findAny();
    }

}