package com.Harevich.ride_service.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RideServiceResponseConstants {
    public static final String RIDE_STATUS_IS_ABSENT = "Parameter ride status is absent";
    public static final String ERROR_WHILE_CONVERTING_RIDE_STATUS = "The field ride status was determined incorrectly";
    public static final String RIDE_NOT_FOUND = "Ride with such id not found";
    public static final String CANNOT_CHANGE_RIDE_STATUS = "You cant change status cause you ride is %s";
}
