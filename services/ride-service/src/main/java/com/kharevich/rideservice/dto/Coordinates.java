package com.kharevich.rideservice.dto;

public record Coordinates(

        Double longitude,

        Double latitude

)
{

    @Override
    public String toString() {
        return longitude + ", " + latitude;
    }

}

