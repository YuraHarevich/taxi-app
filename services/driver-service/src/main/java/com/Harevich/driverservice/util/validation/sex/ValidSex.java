package com.Harevich.driverservice.util.validation.sex;

import com.Harevich.driverservice.util.constants.DriverServiceResponseConstants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SexValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSex {

    String message() default DriverServiceResponseConstants.SEX_PARAM_IS_ABSENT;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
