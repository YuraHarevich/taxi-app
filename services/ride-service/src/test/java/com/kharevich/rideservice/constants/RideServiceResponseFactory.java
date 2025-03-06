package com.kharevich.rideservice.constants;

import com.kharevich.rideservice.sideservices.driver.DriverResponse;
import com.kharevich.rideservice.sideservices.passenger.PassengerResponse;

import java.util.UUID;

import static com.kharevich.rideservice.constants.TestConstants.DEFAULT_DRIVER_CAR_ID;
import static com.kharevich.rideservice.constants.TestConstants.DEFAULT_DRIVER_EMAIL;
import static com.kharevich.rideservice.constants.TestConstants.DEFAULT_DRIVER_ID;
import static com.kharevich.rideservice.constants.TestConstants.DEFAULT_DRIVER_NAME;
import static com.kharevich.rideservice.constants.TestConstants.DEFAULT_DRIVER_SEX;
import static com.kharevich.rideservice.constants.TestConstants.DEFAULT_DRIVER_SURNAME;

public class RideServiceResponseFactory {

    public static DriverResponse createDefaultDriverResponse() {
        return new DriverResponse(
                UUID.fromString(DEFAULT_DRIVER_ID),
                DEFAULT_DRIVER_NAME,
                DEFAULT_DRIVER_SURNAME,
                DEFAULT_DRIVER_EMAIL,
                DEFAULT_DRIVER_SEX,
                UUID.fromString(DEFAULT_DRIVER_CAR_ID)
        );
    }

}
