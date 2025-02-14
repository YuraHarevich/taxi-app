package com.Harevich.ratingservice.util.validation.factory;

import com.Harevich.ratingservice.model.enumerations.RatingPerson;
import com.Harevich.ratingservice.util.validation.PersonValidationService;
import com.Harevich.ratingservice.util.validation.impl.DriverValidationServiceIml;
import com.Harevich.ratingservice.util.validation.impl.PassengerValidationServiceIml;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonValidationServiceFactory {

    private final DriverValidationServiceIml driverValidationServiceIml;

    private final PassengerValidationServiceIml passengerValidationServiceIml;

    public PersonValidationService validatorFor(RatingPerson whoIsRated) {
        return switch (whoIsRated) {
            case DRIVER -> driverValidationServiceIml;
            case PASSENGER -> passengerValidationServiceIml;
            default -> throw new IllegalArgumentException("Invalid rating person type: " + whoIsRated);
        };
    }

}
