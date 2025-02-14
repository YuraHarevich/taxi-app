package com.Harevich.driverservice.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DriverServiceResponseConstants {
    public static final String DRIVER_NOT_FOUND = "Driver not found";
    public static final String DRIVER_DELETED = "Driver with such id is deleted";

    public static final String REPEATED_EMAIL = "Driver with such email already exists";
    public static final String REPEATED_PHONE_NUMBER = "Driver with such phone number already exists";
    public static final String REPEATED_CAR_NUMBER = "Car with such number already exists";

    public static final String CAR_NOT_FOUND = "Car not found";
    public static final String CAR_DELETED = "Car with such id is deleted";
    public static final String CAR_IS_OCCUPIED = "Car with such id is already occupied";
    public static final String SEX_PARAM_IS_ABSENT = "Field sex is invalid";
    public static final String ERROR_WHILE_CONVERTING_SEX = "The field sex was incorrectly determined";
}
