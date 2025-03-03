package com.Harevich.passengerservice.constants;

public class TestConstants {

    public static final String BASIC_NAME = "Maksim";
    public static final String BASIC_SURNAME = "Komissarov";
    public static final String BASIC_MAIL = "mymail@gmail.com";
    public static final String BASIC_NUMBER = "+375332567899";
    public static final String BASIC_UUID = "cf72326f-ef5e-47e2-8d40-d850e1ccd358";
    public static final String SQL_INSERT_DATA = "INSERT INTO passenger (id, name, surname, email, number)\n" +
            "VALUES ('" + BASIC_UUID + "','" + BASIC_NAME + "','" + BASIC_SURNAME + "','" + BASIC_MAIL + "','" + BASIC_NUMBER +"');";
    public static final String SQL_CLEAR_TABLE = "DELETE FROM passenger";
    public static final String BASE_PASSENGERS_URL = "http://localhost:8010/api/v1/passengers";

}
