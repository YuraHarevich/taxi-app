package com.kharevich.ratingservice.constants;

import com.kharevich.ratingservice.sideservices.driver.enumerations.Sex;

public class TestConstants {

    public static final String RATING_SERVICE_ESTIMATE_RIDE = "api/v1/ratings/estimation";
    public static final String RATING_SERVICE_GET_ALL_RATINGS_DRIVER = "api/v1/ratings/driver/all";
    public static final String RATING_SERVICE_GET_ALL_RATINGS_PASSENGER = "api/v1/ratings/passenger/all";
    public static final String RATING_SERVICE_GET_RATING_DRIVER = "api/v1/ratings/driver";
    public static final String RATING_SERVICE_GET_RATING_PASSENGER = "api/v1/ratings/passenger";
    public static final String RATING_SERVICE_HOST_NAME = "http://localhost:8040/";

    public static final String DEFAULT_RIDE_ID = "3fa85f64-5717-4562-b3fc-2c963f66afa2";

    public static final String DEFAULT_DRIVER_ID = "3fa85f64-5717-4562-b3fc-2c963f66afa1";
    public static final String DEFAULT_DRIVER_NAME = "Aschab";
    public static final String DEFAULT_DRIVER_SURNAME = "Tamaev";
    public static final String DEFAULT_DRIVER_EMAIL = "tamaev@gmail.com";
    public static final Sex DEFAULT_DRIVER_SEX = Sex.MALE;
    public static final String DEFAULT_DRIVER_CAR_ID = "3fa85f64-5717-4562-b3fc-2c963f66afa2";

    public static final String DEFAULT_PASSENGER_NAME = "Maksim";
    public static final String DEFAULT_PASSENGER_ID = "3fa85f64-5717-4562-b3fc-2c963f66afa3";
    public static final String DEFAULT_PASSENGER_SURNAME = "Komissarov";
    public static final String DEFAULT_PASSENGER_EMAIL = "mymail@gmail.com";
    public static final String DEFAULT_PASSENGER_NUMBER = "+365446718721";

    public static String DRIVER_SERVICE_URL="/api/v1/drivers?id=" + DEFAULT_DRIVER_ID;
    public static String PASSENGER_SERVICE_URL="/api/v1/passengers?id=" + DEFAULT_PASSENGER_ID;
    public static String RIDE_SERVICE_URL="/api/v1/rides?id=" + DEFAULT_RIDE_ID;

    public static float EXPECTED_PERSONAL_RATING = 5.0f;
    public static int EXPECTED_TOTAL_ELEMENTS = 0;

}
