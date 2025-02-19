package com.Harevich.rideservice.controller.exceptions;

import com.Harevich.rideservice.dto.ErrorMessage;
import com.Harevich.rideservice.exception.GeolocationServiceBadRequestException;
import com.Harevich.rideservice.exception.GeolocationServiceUnavailableException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;

import static com.Harevich.rideservice.util.constants.RideServiceResponseConstants.EXTERNAL_REST_API_BAD_REQUEST;
import static com.Harevich.rideservice.util.constants.RideServiceResponseConstants.EXTERNAL_REST_API_UNAVAILABLE;

public class GeolocationApiErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorMessage message  = null;
        try (InputStream bodyIs = response.body()
                .asInputStream()){
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, ErrorMessage.class);
        } catch (IOException e) {
            return new Exception(EXTERNAL_REST_API_UNAVAILABLE);
        }

        switch (response.status()) {
            case 404:
                return new GeolocationServiceBadRequestException(
                        message.getMessage() != null ? message.getMessage() : EXTERNAL_REST_API_BAD_REQUEST);
            default:
                return new GeolocationServiceUnavailableException(EXTERNAL_REST_API_UNAVAILABLE);
        }
    }

}
