package com.kharevich.ratingservice.util.validation.factory;

import com.kharevich.ratingservice.model.enumerations.RatingPerson;
import com.kharevich.ratingservice.util.validation.PersonValidationService;
import com.kharevich.ratingservice.util.validation.impl.DriverValidationServiceImpl;
import com.kharevich.ratingservice.util.validation.impl.PassengerValidationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonValidationServiceFactory {

    private final DriverValidationServiceImpl driverValidationServiceImpl;

    private final PassengerValidationServiceImpl passengerValidationServiceImpl;

    public PersonValidationService validatorFor(RatingPerson whoIsRated) {
        return switch (whoIsRated) {
            case DRIVER -> driverValidationServiceImpl;
            case PASSENGER -> passengerValidationServiceImpl;
            default -> throw new IllegalArgumentException("Invalid rating person type: " + whoIsRated);
        };
    }

}
