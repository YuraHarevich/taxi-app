package com.Harevich.driverservice.util.converters;

import com.Harevich.driverservice.exception.SexConvertionException;
import com.Harevich.driverservice.model.enumerations.Sex;
import com.Harevich.driverservice.util.constants.DriverServiceResponseConstants;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter
public class SexEnumConverter implements AttributeConverter<Sex, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Sex sex) {
        if (sex == null) {
            throw new SexConvertionException(DriverServiceResponseConstants.SEX_PARAM_IS_ABSENT);
        }
        return sex.getSexCode();
    }

    @Override
    public Sex convertToEntityAttribute(Integer code) {
        return Sex.fromCode(code)
                .orElseThrow(() -> new SexConvertionException(DriverServiceResponseConstants.ERROR_WHILE_CONVERTING_SEX));
    }
}
