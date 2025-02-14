package com.Harevich.rideservice.controller.exceptions;

import com.Harevich.rideservice.exception.DriverServiceInternalError;
import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.persistence.EntityNotFoundException;

import static com.Harevich.rideservice.util.constants.GeolocationResponseConstants.DRIVER_NOT_FOUND;
import static com.Harevich.rideservice.util.constants.GeolocationResponseConstants.DRIVER_SERVICE_UNAVAILABLE;

public class DriverServiceErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new EntityNotFoundException(DRIVER_NOT_FOUND);
        }
        return new DriverServiceInternalError(DRIVER_SERVICE_UNAVAILABLE);
    }
}
