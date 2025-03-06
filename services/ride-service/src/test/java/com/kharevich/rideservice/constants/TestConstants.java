package com.kharevich.rideservice.constants;

import com.kharevich.rideservice.sideservices.driver.Sex;

public class TestConstants {

    public static final String DEFAULT_DRIVER_ID = "12f1400-e29b-41d4-a716-446655440000";
    public static final String DEFAULT_DRIVER_NAME = "Aschab";
    public static final String DEFAULT_DRIVER_SURNAME = "Tamaev";
    public static final String DEFAULT_DRIVER_EMAIL = "tamaev@gmail.com";
    public static final Sex DEFAULT_DRIVER_SEX = Sex.MALE;
    public static final String DEFAULT_DRIVER_CAR_ID = "12f1400-e29b-41d4-a716-446655440001";


    public static final String DEFAULT_PASSENGER_ID = "13f1400-e29b-41d4-a716-446655440000";

    public static final String SQL_INSERT_DATA = "INSERT INTO ride (id, from_address, to_address, price, passenger_id, driver_id, ride_status, created_at, accepted_at, started_at, finished_at)\n" +
            "VALUES \n" +
            "  ('550e8400-e29b-41d4-a716-446655440000', \n" +
            "   'Мендзелеева 14', \n" +
            "   'Сталетава 12', \n" +
            "   25.50, \n" +
            "   '111e8400-e29b-41d4-a716-446655440000', \n" +
            "   '222e8400-e29b-41d4-a716-446655440000', \n" +
            "   'CREATED', \n" +
            "   NOW(), \n" +
            "   NULL, \n" +
            "   NULL, \n" +
            "   NULL);\n";
    public static final String SQL_CLEAR_TABLE = "DELETE FROM passenger";

    public static final String RIDE_SERVICE_APPLY_FOR_DRIVER = "api/v1/rides/order/driver";

    public static final String DRIVER_SERVICE_URL = "api/v1/drivers";
    public static final String PASSENGER_SERVICE_URL = "api/v1/passengers";
}
