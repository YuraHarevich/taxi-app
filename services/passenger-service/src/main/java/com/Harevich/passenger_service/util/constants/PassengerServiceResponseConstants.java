package com.Harevich.passenger_service.util.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PassengerServiceResponseConstants {
    public final String PASSENGER_NOT_FOUND = "Passenger with such id not found";
    public final String PASSENGER_DELETED = "Passenger with such id is deleted";

    public final String REPEATED_EMAIL = "Passenger with such email already exists";
    public final String REPEATED_PHONE_NUMBER = "Passenger with such phone number already exists";
}
