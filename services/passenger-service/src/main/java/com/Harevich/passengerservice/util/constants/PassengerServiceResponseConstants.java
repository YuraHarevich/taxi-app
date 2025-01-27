package com.Harevich.passengerservice.util.constants;

import lombok.experimental.UtilityClass;


public final class PassengerServiceResponseConstants {
    public static final String PASSENGER_NOT_FOUND = "Passenger with such id not found";
    public static final String PASSENGER_DELETED = "Passenger with such id is deleted";

    public static final String REPEATED_EMAIL = "Passenger with such email already exists";
    public static final String REPEATED_PHONE_NUMBER = "Passenger with such phone number already exists";
}
