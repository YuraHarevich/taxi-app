package com.Harevich.passengerservice.util.check.impl;

import com.Harevich.passengerservice.exceptions.PassengersDataRepeatException;
import com.Harevich.passengerservice.repository.PassengerRepository;
import com.Harevich.passengerservice.util.check.PassengerValidation;
import com.Harevich.passengerservice.util.constants.PassengerValidationConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassengerValidationService implements PassengerValidation {
    private final PassengerRepository repository;

    @Override
    public void alreadyExistsByEmail(String email) {
        if(repository.existsByEmail(email))
            throw new PassengersDataRepeatException(PassengerValidationConstants.REPEATED_EMAIL);
    }

    @Override
    public void alreadyExistsByNumber(String number) {
        if(repository.existsByNumber(number))
            throw new PassengersDataRepeatException(PassengerValidationConstants.REPEATED_PHONE_NUMBER);
    }

    @Override
    public void existsById(UUID id) {
        if(!repository.existsById(id))
            throw new EntityNotFoundException(PassengerValidationConstants.PASSENGER_NOT_FOUND);
    }

    @Override
    public void isDeleted(UUID id) {
        repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(PassengerValidationConstants.PASSENGER_DELETED));
    }
}
