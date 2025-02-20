package com.kharevich.rideservice.util.validation.processing_status;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.kharevich.rideservice.util.constants.RideServiceResponseConstants.PROCESSING_STATUS_IS_ABSENT;

@Documented
@Constraint(validatedBy = ProcessingStatusValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessingStatusValid {

    String message() default PROCESSING_STATUS_IS_ABSENT;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
