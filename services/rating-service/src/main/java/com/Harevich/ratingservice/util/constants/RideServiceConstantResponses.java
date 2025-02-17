package com.Harevich.ratingservice.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideServiceConstantResponses {

    public static final String RIDE_IS_ALREADY_ESTIMATED = "Ride %s is already estimated by %s";

    public static final String RIDE_NOT_FOUND = "Ride not found";

    public static final String RIDE_SERVICE_UNAVAILABLE = "Ride service is currently unavailable";

}
