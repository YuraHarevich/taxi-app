package com.Harevich.rideservice.controller.exceptions;

import com.Harevich.rideservice.dto.ErrorMessage;
import com.Harevich.rideservice.exception.GeolocationServiceBadRequestException;
import com.Harevich.rideservice.exception.GeolocationServiceUnavailableException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import static com.Harevich.rideservice.util.constants.RideServiceResponseConstants.OUTSIDER_REST_API_BAD_REQUEST;
import static com.Harevich.rideservice.util.constants.RideServiceResponseConstants.OUTSIDER_REST_API_BAD_UNAVAILABLE;

public class RetreiveMessageErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorMessage message = null;
        try (InputStream bodyIs = response.body()
                .asInputStream()) {
            message = ErrorMessage.builder()
                    .message(bodyIs.toString())
                    .timestamp(LocalDateTime.now())
                    .build();
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }
        switch (response.status()) {
            case 400:
                return new GeolocationServiceBadRequestException(message.getMessage() != null ? message.getMessage() : OUTSIDER_REST_API_BAD_REQUEST);
            case 404:
                return new GeolocationServiceBadRequestException(message.getMessage() != null ? message.getMessage() : OUTSIDER_REST_API_BAD_REQUEST);
            default:
                return new GeolocationServiceUnavailableException(OUTSIDER_REST_API_BAD_UNAVAILABLE);
        }
    }

}
