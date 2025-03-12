package com.kharevich.rideservice.constants;

import com.kharevich.rideservice.sideservices.driver.Sex;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestConstants {

    public static final String DEFAULT_DRIVER_ID = "ea55537b-0f71-427c-96a9-4e5ef350ce28";
    public static final String DEFAULT_DRIVER_NAME = "Aschab";
    public static final String DEFAULT_DRIVER_SURNAME = "Tamaev";
    public static final String DEFAULT_DRIVER_EMAIL = "tamaev@gmail.com";
    public static final Sex DEFAULT_DRIVER_SEX = Sex.MALE;
    public static final String DEFAULT_DRIVER_CAR_ID = "3fa85f64-5717-4562-b3fc-2c963f66afa2";

    public static final String DEFAULT_PASSENGER_NAME = "Maksim";
    public static final String DEFAULT_PASSENGER_ID = "3f8ee7b1-e30b-4ce6-8661-1e2282e3dcb8";
    public static final String DEFAULT_PASSENGER_SURNAME = "Komissarov";
    public static final String DEFAULT_PASSENGER_EMAIL = "mymail@gmail.com";
    public static final String DEFAULT_PASSENGER_NUMBER = "+365446718721";

    public static final String DEFAULT_RIDE_ID = "4f118f57-eb21-4970-8042-e311ed4c3d2c";
    public static final String DEFAULT_FROM_ADDRESS = "Сталетава, 10";
    public static final String DEFAULT_TO_ADDRESS = "Таёжная 5";
    public static final String MODIFIED_FROM_ADDRESS = "ЦУМ";
    public static final String MODIFIED_TO_ADDRESS = "Таёжная 49";
    public static final double DEFAULT_PRICE = 500.00;
    public static final String EXPECTED_RIDE_TOTAL_ELEMENTS= "1";

    public static final String SQL_INSERT_DATA = "INSERT INTO ride (id, from_address, to_address, price, passenger_id, driver_id, ride_status, created_at, accepted_at, started_at, finished_at)\n" +
            "VALUES \n" +
            "  ('"+ DEFAULT_RIDE_ID +"', \n" +
            "   '"+ DEFAULT_FROM_ADDRESS +"', \n" +
            "   '"+ DEFAULT_TO_ADDRESS +"', \n" +
            "   " + DEFAULT_PRICE +", \n" +
            "   '"+ DEFAULT_PASSENGER_ID +"', \n" +
            "   '"+ DEFAULT_DRIVER_ID +"', \n" +
            "   0, \n" +
            "   NOW(), \n" +
            "   NULL, \n" +
            "   NULL, \n" +
            "   NULL);\n";
    public static final String SQL_CLEAR_TABLES = "DELETE FROM ride";

    public static final String RIDE_SERVICE_APPLY_FOR_DRIVER = "api/v1/rides/order/driver";
    public static final String RIDE_SERVICE_CREATE_ORDER = "api/v1/rides/order/passenger";
    public static final String RIDE_SERVICE_CREATE_RIDE = "api/v1/rides";
    public static final String RIDE_SERVICE_UPDATE_RIDE = "api/v1/rides";
    public static final String RIDE_SERVICE_CHANGE_RIDE_STATUS = "api/v1/rides/change-status";
    public static final String RIDE_SERVICE_GET_RIDE_BY_ID = "api/v1/rides";
    public static final String RIDE_SERVICE_GET_ALL_RIDES = "api/v1/rides/all";
    public static final String RIDE_SERVICE_GET_ALL_RIDES_PASSENGER_ID = "api/v1/rides/all/passenger";
    public static final String RIDE_SERVICE_GET_ALL_RIDES_DRIVER_ID= "api/v1/rides/all/driver";

    public static String DRIVER_SERVICE_URL="/api/v1/drivers?id=" + DEFAULT_DRIVER_ID;
    public static final String GEOLOCATION_COORDINATES_SERVICE_URL = "/geocode/search";
    public static final String GEOLOCATION_DIRECTIONS_SERVICE_URL = "/v2/directions/driving-car";
    public static final String PASSENGER_SERVICE_URL = "/api/v1/passengers?id=" + DEFAULT_PASSENGER_ID;
    public static final String RIDE_SERVICE_HOST_NAME = "http://localhost:8030/";

    public static final Map<String,Object> GEOLOCATION_COORDINATES_RESPONSE_MAP = Map.of(
            "features", List.of(
                    Map.of(
                            "geometry", Map.of(
                                    "coordinates", List.of(27.56667, 53.9)
                            )
                    )
            )
    );

    public static final Map<String,Object> GEOLOCATION_DIRECTIONS_RESPONSE_MAP = Map.of(
            "features", List.of(
                    Map.of(
                            "properties", Map.of(
                                    "summary", Map.of(
                                            "distance", 12345.67
                                    )
                            )
                    )
            )
    );



}
