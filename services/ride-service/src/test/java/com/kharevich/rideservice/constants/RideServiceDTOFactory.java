package com.kharevich.rideservice.constants;

import com.kharevich.rideservice.dto.request.RideRequest;
import com.kharevich.rideservice.sideservices.driver.DriverResponse;
import com.kharevich.rideservice.sideservices.passenger.PassengerResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.kharevich.rideservice.constants.TestConstants.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideServiceDTOFactory {

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

    public static PassengerResponse createDefaultPassengerResponse() {
        return new PassengerResponse(
                UUID.fromString(DEFAULT_PASSENGER_ID),
                DEFAULT_PASSENGER_NAME,
                DEFAULT_PASSENGER_SURNAME,
                DEFAULT_PASSENGER_EMAIL,
                DEFAULT_PASSENGER_NUMBER
        );
    }

    public static RideRequest createDefaultRideRequest() {
        return new RideRequest(
                DEFAULT_FROM_ADDRESS,
                DEFAULT_TO_ADDRESS,
                UUID.fromString(DEFAULT_PASSENGER_ID)
        );
    }

    public static RideRequest createInvalidRideRequest() {
        return new RideRequest(
                "",
                "",
                UUID.fromString(DEFAULT_PASSENGER_ID)
        );
    }

    public static RideRequest createDefaultUpdateRideRequest() {
        return new RideRequest(
                MODIFIED_FROM_ADDRESS,
                MODIFIED_TO_ADDRESS,
                UUID.fromString(DEFAULT_PASSENGER_ID)
        );
    }

    public static RideRequest createInvalidUpdateRideRequest() {
        return new RideRequest(
                "",
                "",
                UUID.fromString(DEFAULT_PASSENGER_ID)
        );
    }

}
