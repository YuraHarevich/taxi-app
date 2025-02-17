package com.Harevich.rideservice.controller.exceptions;

import com.Harevich.rideservice.exception.PassengerServiceInternalErrorException;
import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.persistence.EntityNotFoundException;

import static com.Harevich.rideservice.util.constants.RideServiceResponseConstants.*;

public class PassengerServiceErrorDecoder implements ErrorDecoder{
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new EntityNotFoundException(PASSENGER_NOT_FOUND);
        }
        return new PassengerServiceInternalErrorException(PASSENGER_SERVICE_UNAVAILABLE);
    }
}
