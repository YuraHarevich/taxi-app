package com.kharevich.rideservice.util.validation.ride_status;

import com.kharevich.rideservice.util.constants.RideServiceResponseConstants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = RideValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RideStatusValid {

    String message() default RideServiceResponseConstants.RIDE_STATUS_IS_ABSENT;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
