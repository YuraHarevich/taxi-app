package com.Harevich.ratingservice.controller.exceptions;

import com.Harevich.ratingservice.exception.PassengerNotFoundException;
import com.Harevich.ratingservice.exception.PassengerServiceInternalErrorException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;

import static com.Harevich.ratingservice.util.constants.RideServiceConstantResponses.PASSENGER_NOT_FOUND;
import static com.Harevich.ratingservice.util.constants.RideServiceConstantResponses.PASSENGER_SERVICE_UNAVAILABLE;


public class PassengerServiceErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new PassengerNotFoundException(PASSENGER_NOT_FOUND);
        }
        return new PassengerServiceInternalErrorException(PASSENGER_SERVICE_UNAVAILABLE);
    }
}