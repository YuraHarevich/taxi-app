package com.Harevich.rideservice.util.validation.driver;

import java.util.UUID;

public interface DriverValidation {
    void throwExceptionIfDriverDoesNotExist(UUID driverId);
}
