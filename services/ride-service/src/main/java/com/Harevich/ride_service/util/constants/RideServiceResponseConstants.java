package com.Harevich.ride_service.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RideServiceResponseConstants {
    public static final String RIDE_STATUS_IS_ABSENT = "Parameter ride status is absent";
    public static final Object ERROR_WHILE_CONVERTING_RIDE_STATUS = "The field ride status was determined incorrectly";
}
