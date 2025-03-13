package com.Harevich.driverservice.constants;

import com.Harevich.driverservice.dto.request.CarRequest;

import static com.Harevich.driverservice.constants.TestConstants.BASIC_CAR_BRAND;
import static com.Harevich.driverservice.constants.TestConstants.BASIC_CAR_COLOR;
import static com.Harevich.driverservice.constants.TestConstants.BASIC_CAR_NUMBER;

public class CarRequestFactory {

    public static CarRequest createDefaultRequest() {
        return new CarRequest(
                "black",
                "7777 BB-1",
                "porsche"
        );
    }

    public static CarRequest createInvalidNumberRequest() {
        return new CarRequest(
                BASIC_CAR_COLOR,
                "invalid",
                BASIC_CAR_BRAND
        );
    }

    public static CarRequest createRepeatedNumberRequest() {
        return new CarRequest(
                "black",
                BASIC_CAR_NUMBER,
                "porsche"
        );
    }

    public static CarRequest createUpdateRequest() {
        return new CarRequest(
                "black",
                "7778 BB-7",
                "BMW"
        );
    }

    public static CarRequest createInvalidRequest() {
        return new CarRequest(
                "",
                "invalid",
                ""
        );
    }

}
