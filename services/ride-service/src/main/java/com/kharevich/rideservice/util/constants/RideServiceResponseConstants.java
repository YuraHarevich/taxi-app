package com.kharevich.rideservice.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideServiceResponseConstants {

    public static final String RIDE_STATUS_IS_ABSENT = "Parameter ride status is absent";

    public static final String ERROR_WHILE_CONVERTING_RIDE_STATUS = "The field ride status was determined incorrectly";

    public static final String RIDE_NOT_FOUND = "Ride with such id not found";

    public static final String CANNOT_CHANGE_RIDE_STATUS = "You cant change status cause you ride is %s";

    public static final String ADDRESS_NOT_FOUND = "Your address %s not found, you can try to write it on belarusian)";

    public static final String EXTERNAL_REST_API_FORBIDDEN = "invalid api key";

    public static final String EXTERNAL_REST_API_BAD_REQUEST = "Address name is invalid";

    public static final String EXTERNAL_REST_API_UNAVAILABLE = "Geolocational api that we use to calculate price is unavailable";

    public static final String UPDATE_NOT_ALLOWED = "You cant update ride when status is different from created";

    public static final String DRIVER_IS_BUSY = "Now this driver is busy";

    public static final String UNEXPECTED_SERVER_ERROR = "unexpected inner behavior";

    public static final String ERROR_WHILE_CONVERTING_PROCESSING_STATUS = "The field processing status was determined incorrectly";

    public static final String PROCESSING_STATUS_IS_ABSENT = "Parameter processing status is absent";

    public static final String PASSENGER_NOT_FOUND = "passenger with id %s not found";

    public static final String PASSENGER_SERVICE_UNAVAILABLE = "Passenger service is currently unavailable";

    public static final String DRIVER_SERVICE_UNAVAILABLE = "Driver service is currently unavailable";

    public static final String DRIVER_NOT_FOUND = "Driver with id not found";

}
