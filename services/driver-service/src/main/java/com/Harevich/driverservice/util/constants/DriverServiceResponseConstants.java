package com.Harevich.driverservice.util.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DriverServiceResponseConstants {
    public final String DRIVER_NOT_FOUND = "Driver with such id not found";
    public final String DRIVER_DELETED = "Driver with such id is deleted";

    public final String REPEATED_EMAIL = "Driver with such email already exists";
    public final String REPEATED_PHONE_NUMBER = "Driver with such phone number already exists";
}
