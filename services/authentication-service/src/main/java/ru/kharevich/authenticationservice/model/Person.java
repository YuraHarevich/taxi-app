package ru.kharevich.authenticationservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum Person {


    DRIVER(100),

    PASSENGER(200);

    private final int code;

    public static Optional<Person> fromCode(int code) {
        return Arrays.stream(Person.values())
                .filter(person -> person.code == code)
                .findAny();
    }

}
