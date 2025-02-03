package com.Harevich.ride_service.util.validation.ride_status;

import com.Harevich.ride_service.model.enumerations.RideStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class RideValidation implements ConstraintValidator<RideStatusValid, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isEmpty()) {
            return true;
        }
        return Arrays.stream(RideStatus.values())
                .anyMatch(status -> status.name().equals(value));
    }

}