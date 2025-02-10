package com.Harevich.rating_service.model.enumerations;

public enum RatingPerson {

    PASSENGER,
    DRIVER;

    @Override
    public String toString() {
        return name();
    }

}
