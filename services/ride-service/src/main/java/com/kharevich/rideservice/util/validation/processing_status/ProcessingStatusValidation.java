package com.kharevich.rideservice.util.validation.processing_status;

import com.kharevich.rideservice.model.enumerations.ProcessingStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ProcessingStatusValidation  implements ConstraintValidator<ProcessingStatusValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isEmpty()) {
            return true;
        }
        return Arrays.stream(ProcessingStatus.values())
                .anyMatch(status -> status.name().equals(value));
    }

}
