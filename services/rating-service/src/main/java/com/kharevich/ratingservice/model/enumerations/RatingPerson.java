package com.kharevich.ratingservice.model.enumerations;

public enum RatingPerson {

    PASSENGER,
    DRIVER;

    @Override
    public String toString() {
        return name();
    }

}
