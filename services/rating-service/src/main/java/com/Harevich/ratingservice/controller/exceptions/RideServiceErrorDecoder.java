package com.Harevich.ratingservice.controller.exceptions;

import com.Harevich.ratingservice.exception.RideNotFoundException;
import com.Harevich.ratingservice.exception.RideServiceInternalErrorException;
import feign.Response;
import feign.codec.ErrorDecoder;

import static com.Harevich.ratingservice.util.constants.RideServiceConstantResponses.RIDE_NOT_FOUND;
import static com.Harevich.ratingservice.util.constants.RideServiceConstantResponses.RIDE_SERVICE_UNAVAILABLE;

public class RideServiceErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new RideNotFoundException(RIDE_NOT_FOUND);
        }
        return new RideServiceInternalErrorException(RIDE_SERVICE_UNAVAILABLE);
    }
}
