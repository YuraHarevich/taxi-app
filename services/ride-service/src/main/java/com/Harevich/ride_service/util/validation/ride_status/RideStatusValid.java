package com.Harevich.ride_service.util.validation.ride_status;

import com.Harevich.ride_service.util.constants.RideServiceResponseConstants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RideValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RideStatusValid {

    String message() default RideServiceResponseConstants.RIDE_STATUS_IS_ABSENT;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
