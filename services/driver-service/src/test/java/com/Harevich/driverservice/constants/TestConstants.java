package com.Harevich.driverservice.constants;

public class TestConstants {

    public static final String BASIC_SERVER_HOST = "http://localhost:8020/";

    public static final String BASIC_DRIVER_UUID = "cf72326f-ef5e-47e2-8d40-d850e1ccd358";
    public static final String BASIC_DRIVER_NAME = "Maksim";
    public static final String BASIC_DRIVER_SURNAME = "Komissarov";
    public static final String BASIC_DRIVER_MAIL = "mymail@gmail.com";
    public static final String BASIC_DRIVER_NUMBER = "+375332567899";
    public static final int BASIC_DRIVER_SEX_CODE = 1;

    public static final String SQL_INSERT_DATA_DRIVER = "INSERT INTO drivers (id, name, surname, email, number, sex)\n" +
            "VALUES ('" + BASIC_DRIVER_UUID + "','" + BASIC_DRIVER_NAME + "','" + BASIC_DRIVER_SURNAME + "','"
            + BASIC_DRIVER_MAIL + "','"+ BASIC_DRIVER_NUMBER + "','" + BASIC_DRIVER_SEX_CODE +"');";
    public static final String SQL_CLEAR_TABLE_DRIVERS = "DELETE FROM drivers";
    public static final String RELATIVE_CREATE_DRIVER_URL = "api/v1/drivers/create";
    public static final String RELATIVE_UPDATE_DRIVER_URL = "api/v1/drivers/update";
    public static final String RELATIVE_GET_ID_DRIVER_URL = "api/v1/drivers";
    public static final String RELATIVE_DELETE_DRIVER_URL = "api/v1/drivers";
    public static final String RELATIVE_ASSIGN_CAR_TO_DRIVER_URL = "api/v1/drivers/changeCar";

    public static final String BASIC_CAR_UUID = "cf72326f-ef5e-47e2-8d40-d850e1ccd351";
    public static final String RELATIVE_CREATE_CAR_URL = "api/v1/cars/create";
    public static final String RELATIVE_UPDATE_CAR_URL = "api/v1/cars/update";
    public static final String RELATIVE_GET_ID_CAR_URL = "api/v1/cars/id";
    public static final String RELATIVE_GET_NUMBER_CAR_URL = "api/v1/cars/number";
    public static final String RELATIVE_DELETE_CAR_URL = "api/v1/cars";
    public static final String RELATIVE_GET_AVAILABLE_CARS_URL = "api/v1/cars/available";
    public static final String BASIC_CAR_COLOR = "red";
    public static final String BASIC_CAR_NUMBER = "7777 BB-7";
    public static final String BASIC_CAR_BRAND = "BMW";

    public static final String SQL_CLEAR_TABLE_CARS = "DELETE FROM cars";
    public static final String SQL_INSERT_DATA_CAR = "INSERT INTO cars (id, color, number, brand)\n" +
            "VALUES ('" + BASIC_CAR_UUID + "','" + BASIC_CAR_COLOR + "','" + BASIC_CAR_NUMBER + "','" + BASIC_CAR_BRAND + "');";

    public static final String SQL_CLEAR_MERGE_TABLE = "DELETE FROM driver_car_merge";
    public static final String SQL_INSERT_DATA_CAR_DRIVER_MERGE = "INSERT INTO driver_car_merge (driver_id, car_id)\n" +
            "VALUES ('" + BASIC_DRIVER_UUID + "','" + BASIC_CAR_UUID + "');";

}
