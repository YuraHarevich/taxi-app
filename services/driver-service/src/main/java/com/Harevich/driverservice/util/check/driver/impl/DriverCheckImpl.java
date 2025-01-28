package com.Harevich.driverservice.util.check.impl;

import com.Harevich.driverservice.exception.UniqueException;
import com.Harevich.driverservice.repository.DriverRepository;
import com.Harevich.driverservice.util.check.DriverCheck;
import com.Harevich.driverservice.util.constants.DriverServiceResponseConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverCheckImpl implements DriverCheck {
    private final DriverRepository repository;

    @Override
    public void alreadyExistsByEmail(String email) {
        if(repository.existsByEmail(email))
            throw new UniqueException(DriverServiceResponseConstants.REPEATED_EMAIL);
    }

    @Override
    public void alreadyExistsByNumber(String number) {
        if(repository.existsByNumber(number))
            throw new UniqueException(DriverServiceResponseConstants.REPEATED_PHONE_NUMBER);
    }

    @Override
    public void existsById(UUID id) {
        if(!repository.existsById(id))
            throw new UniqueException(DriverServiceResponseConstants.DRIVER_NOT_FOUND);
    }

    @Override
    public void isDeleted(UUID id) {
        repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(DriverServiceResponseConstants.DRIVER_DELETED));
    }
}
