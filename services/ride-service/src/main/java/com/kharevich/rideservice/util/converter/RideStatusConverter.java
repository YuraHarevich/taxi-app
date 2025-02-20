package com.kharevich.rideservice.util.converter;

import com.kharevich.rideservice.exception.RideStatusConvertionException;
import com.kharevich.rideservice.model.enumerations.RideStatus;
import com.kharevich.rideservice.util.constants.RideServiceResponseConstants;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RideStatusConverter implements AttributeConverter<RideStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(RideStatus rideStatus) {
        if (rideStatus == null) {
            throw new RideStatusConvertionException(RideServiceResponseConstants.RIDE_STATUS_IS_ABSENT);
        }
        return rideStatus.getRideCode();
    }

    @Override
    public RideStatus convertToEntityAttribute(Integer code) {
        return RideStatus.fromCode(code)
                .orElseThrow(() -> new RideStatusConvertionException(RideServiceResponseConstants.ERROR_WHILE_CONVERTING_RIDE_STATUS));
    }

}
