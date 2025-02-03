package com.Harevich.ride_service.util.converter;

import com.Harevich.ride_service.model.enumerations.RideStatus;
import com.Harevich.ride_service.util.constants.RideServiceResponseConstants;

@Converter
public class RideStatusConverter implements AttributeConverter<RideStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(RideStatus rideStatus) {
        if (rideStatus == null) {
            throw new RideStatusConvertionException(RideServiceResponseConstants.RIDE_STATUS_IS_ABSENT);
        }
        return sex.getSexCode();
    }

    @Override
    public RideStatus convertToEntityAttribute(Integer code) {
        return RideStatus.fromCode(code)
                .orElseThrow(() -> new RideStatusConvertionException(RideServiceResponseConstants.ERROR_WHILE_CONVERTING_RIDE_STATUS));
    }
}
