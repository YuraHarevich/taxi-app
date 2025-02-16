package com.Harevich.rideservice.controller.exceptions;

import com.Harevich.rideservice.dto.ErrorMessage;
import com.Harevich.rideservice.exception.GeolocationServiceBadRequestException;
import com.Harevich.rideservice.exception.GeolocationServiceUnavailableException;
import com.Harevich.rideservice.exception.PassengerServiceInternalError;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.persistence.EntityNotFoundException;

import java.io.IOException;
import java.io.InputStream;

import static com.Harevich.rideservice.util.constants.RideServiceResponseConstants.*;

public class PassengerServiceErrorDecoder implements ErrorDecoder{
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new EntityNotFoundException(PASSENGER_NOT_FOUND);
        }
        return new PassengerServiceInternalError(PASSENGER_SERVICE_UNAVAILABLE);
    }
}
