package com.kharevich.ratingservice.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideServiceConstantResponses {

    public static final String RIDE_IS_ALREADY_ESTIMATED = "Ride %s is already estimated by %s";

    public static final String DRIVER_NOT_FOUND = "Driver %s not found";

    public static final String PASSENGER_NOT_FOUND = "Passenger %s not found";

    public static final String DRIVER_SERVICE_UNAVAILABLE = "Driver service is currently unavailable";

    public static final String PASSENGER_SERVICE_UNAVAILABLE = "Passenger service is currently unavailable";
  
    public static final String RIDE_NOT_FOUND = "Ride not found";

    public static final String RIDE_SERVICE_UNAVAILABLE = "Ride service is currently unavailable";

    public static final String RIDE_IS_NOT_FINISHED = "Ride is not yet finished";

}
