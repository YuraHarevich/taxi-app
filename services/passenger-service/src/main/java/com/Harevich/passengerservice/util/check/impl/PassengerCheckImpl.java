package com.Harevich.passengerservice.util.check.impl;

import com.Harevich.passengerservice.exceptions.UniqueException;
import com.Harevich.passengerservice.repository.PassengerRepository;
import com.Harevich.passengerservice.util.check.PassengerCheck;
import com.Harevich.passengerservice.util.constants.PassengerServiceResponseConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassengerCheckImpl implements PassengerCheck {
    private final PassengerRepository repository;

    @Override
    public void alreadyExistsByEmail(String email) {
        if(repository.existsByEmail(email))
            throw new UniqueException(PassengerServiceResponseConstants.REPEATED_EMAIL);
    }

    @Override
    public void alreadyExistsByNumber(String number) {
        if(repository.existsByNumber(number))
            throw new UniqueException(PassengerServiceResponseConstants.REPEATED_PHONE_NUMBER);
    }

    @Override
    public void existsById(UUID id) {
        if(!repository.existsById(id))
            throw new EntityNotFoundException(PassengerServiceResponseConstants.PASSENGER_NOT_FOUND);
    }

    @Override
    public void isDeleted(UUID id) {
        repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(PassengerServiceResponseConstants.PASSENGER_DELETED));
    }
}
