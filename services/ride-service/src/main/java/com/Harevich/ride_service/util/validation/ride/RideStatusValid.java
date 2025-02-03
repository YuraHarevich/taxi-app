package com.Harevich.ride_service.util.validation.ride;

import com.Harevich.ride_service.util.constants.RideServiceResponseConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Documented
@Constraint(validatedBy = SexValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RideStatusValid {
    String message() default RideServiceResponseConstants.RIDE_STATUS_IS_ABSENT;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
