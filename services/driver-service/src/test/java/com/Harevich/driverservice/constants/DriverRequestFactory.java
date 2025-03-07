package com.Harevich.driverservice.constants;

import com.Harevich.driverservice.dto.request.DriverRequest;
import com.Harevich.driverservice.model.enumerations.Sex;

import static com.Harevich.driverservice.constants.TestConstants.BASIC_DRIVER_MAIL;
import static com.Harevich.driverservice.constants.TestConstants.BASIC_DRIVER_NAME;
import static com.Harevich.driverservice.constants.TestConstants.BASIC_DRIVER_NUMBER;
import static com.Harevich.driverservice.constants.TestConstants.BASIC_DRIVER_SURNAME;

public class DriverRequestFactory {

    public static DriverRequest createDefaultRequest() {
        return new DriverRequest(
                BASIC_DRIVER_NAME,
                BASIC_DRIVER_SURNAME,
                "jury.harevich@gmail.com",
                "+375447525701",
                "MALE"
        );
    }

    public static DriverRequest createInvalidEmailRequest() {
        return new DriverRequest(
                "Yura",
                "Komissarov",
                "invalid",
                "+375445527898",
                Sex.MALE.toString()
        );
    }

    public static DriverRequest createInvalidNumberRequest() {
        return new DriverRequest(
                "Yura",
                "Komissarov",
                "sdffgs@gmail.com",
                "invalid",
                Sex.MALE.toString()
        );
    }

    public static DriverRequest createRepeatedEmailRequest() {
        return new DriverRequest(
                "Yura",
                "Komissarov",
                BASIC_DRIVER_MAIL,
                "+375332567891",
                Sex.MALE.toString()
        );
    }

    public static DriverRequest createRepeatedNumberRequest() {
        return new DriverRequest(
                "Yura",
                "Komissarov",
                "someMail@gmail.com",
                BASIC_DRIVER_NUMBER,
                Sex.MALE.toString()
        );
    }

    public static DriverRequest createUpdateRequest() {
        return new DriverRequest(
                "UpdatedName",
                "UpdatedSurname",
                "updatedmail@gmail.com",
                "+375332567890",
                Sex.MALE.toString()
        );
    }

    public static DriverRequest createInvalidRequest() {
        return new DriverRequest(
                "",
                "",
                "invalid-email",
                "invalid",
                Sex.MALE.toString()
        );
    }

}
