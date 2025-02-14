package com.Harevich.rideservice.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GeolocationResponseConstants {

    public static final String MAIN_OBJECT_NAME = "features";

    public static final String COORDINATES_FIRST_INNER_OBJECT_NAME = "geometry";

    public static final String DISTANCE_FIRST_INNER_OBJECT_NAME = "properties";

    public static final String COORDINATES_OBJECT_NAME = "coordinates";

    public static final String DISTANCE_SECOND_INNER_OBJECT_NAME = "summary";

    public static final String DISTANCE_OBJECT_NAME = "distance";

    public static final int MAIN_OBJECT_INNER_NUMBER = 0;

    public static final int COORDINATES_INNER_LONGITUDE_NUMBER = 0;

    public static final int COORDINATES_INNER_LATITUDE_NUMBER = 1;

    public static final int KILOMETER_TO_METER_DIVIDE_CONSTANT = 1000;

    public static final String DRIVER_SERVICE_UNAVAILABLE = "Driver service is currently unavailable";

}
