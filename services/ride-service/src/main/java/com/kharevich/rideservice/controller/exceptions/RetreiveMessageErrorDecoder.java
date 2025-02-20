package com.kharevich.rideservice.controller.exceptions;

import com.kharevich.rideservice.dto.ErrorMessage;
import com.kharevich.rideservice.exception.GeolocationServiceBadRequestException;
import com.kharevich.rideservice.exception.GeolocationServiceUnavailableException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

import static com.kharevich.rideservice.util.constants.RideServiceResponseConstants.EXTERNAL_REST_API_FORBIDDEN;
import static com.kharevich.rideservice.util.constants.RideServiceResponseConstants.EXTERNAL_REST_API_UNAVAILABLE;
import static com.kharevich.rideservice.util.constants.RideServiceResponseConstants.EXTERNAL_REST_API_BAD_REQUEST;

@Slf4j
public class RetreiveMessageErrorDecoder implements ErrorDecoder {

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
            case 403:
                return new GeolocationServiceBadRequestException(
                        message.getMessage() != null ? message.getMessage() : EXTERNAL_REST_API_FORBIDDEN);
            case 404:
                return new GeolocationServiceBadRequestException(
                        message.getMessage() != null ? message.getMessage() : EXTERNAL_REST_API_BAD_REQUEST);
            default:
                return new GeolocationServiceUnavailableException(EXTERNAL_REST_API_UNAVAILABLE);
        }
    }
}

