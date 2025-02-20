package com.kharevich.ratingservice.controller.exceptions;

import com.kharevich.ratingservice.exception.DriverNotFoundException;
import com.kharevich.ratingservice.exception.DriverServiceInternalErrorException;
import feign.Response;
import feign.codec.ErrorDecoder;

import static com.kharevich.ratingservice.util.constants.RideServiceConstantResponses.DRIVER_NOT_FOUND;
import static com.kharevich.ratingservice.util.constants.RideServiceConstantResponses.DRIVER_SERVICE_UNAVAILABLE;

public class DriverServiceErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new DriverNotFoundException(DRIVER_NOT_FOUND);
        }
        return new DriverServiceInternalErrorException(DRIVER_SERVICE_UNAVAILABLE);
    }
}
