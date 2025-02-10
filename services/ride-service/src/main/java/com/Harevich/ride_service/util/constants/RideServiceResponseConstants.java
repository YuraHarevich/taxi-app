package com.Harevich.ride_service.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideServiceResponseConstants {

    public static final String RIDE_STATUS_IS_ABSENT = "Parameter ride status is absent";

    public static final String ERROR_WHILE_CONVERTING_RIDE_STATUS = "The field ride status was determined incorrectly";

    public static final String RIDE_NOT_FOUND = "Ride with such id not found";

    public static final String CANNOT_CHANGE_RIDE_STATUS = "You cant change status cause you ride is %s";

    public static final String ADDRESS_NOT_FOUND = "Your address %s not found, you can try to write it on belarusian)";

    public static final String OUTSIDER_REST_API_BAD_REQUEST = "Address name is invalid";

    public static final String OUTSIDER_REST_API_BAD_UNAVAILABLE = "Geolocational api that we use to calculate price is unavailable";

    public static final String UPDATE_NOT_ALLOWED = "You cant update ride when status is different from created";
    public static final String DRIVER_IS_BUSY = "Now this driver is busy";
}
