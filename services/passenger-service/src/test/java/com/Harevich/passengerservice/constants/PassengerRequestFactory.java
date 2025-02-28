package com.Harevich.passengerservice.constants;

import com.Harevich.passengerservice.dto.PassengerRequest;

public class PassengerRequestFactory {

    public static PassengerRequest createDefaultRequest() {
        return new PassengerRequest(
                "Yura",
                "Harevich",
                "1mymail@gmail.com",
                "+375332567829"
        );
    }

    public static PassengerRequest createInvalidEmailRequest() {
        return new PassengerRequest(
                "Maksim",
                "Komissarov",
                "mymaimail.com",
                "+375332567899"
        );
    }

    public static PassengerRequest createInvalidPhoneRequest() {
        return new PassengerRequest(
                "Maksim",
                "Komissarov",
                "mymaim@gmail.com",
                "+399"
        );
    }

    public static PassengerRequest createRepeatedEmailRequest() {
        return new PassengerRequest(
                "Maksim",
                "Komissarov",
                TestConstants.BASIC_MAIL,
                "+375332567899"
        );
    }

    public static PassengerRequest createUpdateRequest() {
        return new PassengerRequest(
                "UpdatedName",
                "UpdatedSurname",
                "updatedmail@gmail.com",
                "+375332567890"
        );
    }

    public static PassengerRequest createInvalidRequest() {
        return new PassengerRequest(
                "",
                "",
                "invalid-email",
                "123"
        );
    }
}
