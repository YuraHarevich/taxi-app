package com.Harevich.ratingservice.controller.exceptions;

import com.Harevich.ratingservice.exception.DriverNotFoundException;
import com.Harevich.ratingservice.exception.DriverServiceInternalErrorException;
import feign.Response;
import feign.codec.ErrorDecoder;

import static com.Harevich.ratingservice.util.constants.RideServiceConstantResponses.DRIVER_NOT_FOUND;
import static com.Harevich.ratingservice.util.constants.RideServiceConstantResponses.DRIVER_SERVICE_UNAVAILABLE;

public class DriverServiceErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new DriverNotFoundException(DRIVER_NOT_FOUND);
        }
        return new DriverServiceInternalErrorException(DRIVER_SERVICE_UNAVAILABLE);
    }
}
