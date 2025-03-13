package com.kharevich.ratingservice.constants;

import com.kharevich.ratingservice.dto.request.RatingRequest;
import com.kharevich.ratingservice.model.enumerations.RatingPerson;
import com.kharevich.ratingservice.sideservices.driver.DriverResponse;
import com.kharevich.ratingservice.sideservices.passenger.PassengerResponse;
import com.kharevich.ratingservice.sideservices.ride.RideResponse;
import com.kharevich.ratingservice.sideservices.ride.enumerations.RideStatus;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

import static com.kharevich.ratingservice.constants.TestConstants.*;

public class RatingServiceDTOFactory {

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

    public static RideResponse createDefaultRideResponse() {
        return new RideResponse(
                UUID.fromString(DEFAULT_RIDE_ID),
                "Мендзялеева 12",
                "Сталетава 10",
                BigDecimal.TEN,
                UUID.fromString(DEFAULT_PASSENGER_ID),
                UUID.fromString(DEFAULT_DRIVER_ID),
                RideStatus.FINISHED,
                Duration.ZERO
        );
    }

    public static RatingRequest createDefaultRatingRequest() {
        return new RatingRequest(
                UUID.fromString(DEFAULT_RIDE_ID),
                3,
                RatingPerson.DRIVER,
                "awesome"
        );
    }

}
