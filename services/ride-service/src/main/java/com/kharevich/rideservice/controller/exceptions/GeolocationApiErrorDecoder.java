package com.kharevich.rideservice.controller.exceptions;

import com.kharevich.rideservice.dto.ErrorMessage;
import com.kharevich.rideservice.exception.GeolocationServiceBadRequestException;
import com.kharevich.rideservice.exception.GeolocationServiceUnavailableException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;

import static com.kharevich.rideservice.util.constants.RideServiceResponseConstants.OUTSIDER_REST_API_BAD_REQUEST;
import static com.kharevich.rideservice.util.constants.RideServiceResponseConstants.OUTSIDER_REST_API_BAD_UNAVAILABLE;

public class GeolocationApiErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorMessage message  = null;
        try (InputStream bodyIs = response.body()
                .asInputStream()){
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, ErrorMessage.class);
        } catch (IOException e) {
            return new Exception(OUTSIDER_REST_API_BAD_UNAVAILABLE);
        }

        switch (response.status()) {
            case 400:
            case 404:
                return new GeolocationServiceBadRequestException(
                        message.getMessage() != null ? message.getMessage() : OUTSIDER_REST_API_BAD_REQUEST);
            default:
                return new GeolocationServiceUnavailableException(OUTSIDER_REST_API_BAD_UNAVAILABLE);
        }
    }
}

