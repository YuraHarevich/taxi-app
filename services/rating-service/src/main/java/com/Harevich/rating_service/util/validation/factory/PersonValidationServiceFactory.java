package com.Harevich.rating_service.util.validation.factory;

import com.Harevich.rating_service.model.enumerations.VotingPerson;
import com.Harevich.rating_service.util.validation.PersonValidationService;
import com.Harevich.rating_service.util.validation.impl.DriverValidationServiceIml;
import com.Harevich.rating_service.util.validation.impl.PassengerValidationServiceIml;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonValidationServiceFactory {
    private final DriverValidationServiceIml driverValidationServiceIml;
    private final PassengerValidationServiceIml passengerValidationServiceIml;

    public PersonValidationService getService(VotingPerson whoVotes) {
        switch (whoVotes){
            case DRIVER:
                return driverValidationServiceIml;
            case PASSENGER:
                return passengerValidationServiceIml;
            default:
                throw new IllegalArgumentException("Invalid VotingPerson type: " + whoVotes);
                //todo: обработка этого вот
        }
    }
}
