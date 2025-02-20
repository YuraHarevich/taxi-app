package com.kharevich.ratingservice.controller.exceptions;

import com.kharevich.ratingservice.exception.RideNotFoundException;
import com.kharevich.ratingservice.exception.RideServiceInternalErrorException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.kharevich.ratingservice.util.constants.RideServiceConstantResponses.RIDE_NOT_FOUND;
import static com.kharevich.ratingservice.util.constants.RideServiceConstantResponses.RIDE_SERVICE_UNAVAILABLE;

@Slf4j
public class RideServiceErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        String responseBody = "";

        try {
            if (response.body() != null) {
                responseBody = Util.toString(response.body().asReader());
            }
        } catch (IOException e) {
            log.error("Failed to read response body", e);
        }

        log.error("Feign error in method [{}]: status={}, reason={}, responseBody={}",
                methodKey, response.status(), response.reason(), responseBody);

        if (response.status() == 404) {
            return new RideNotFoundException(RIDE_NOT_FOUND);
        }
        return new RideServiceInternalErrorException(RIDE_SERVICE_UNAVAILABLE);
    }
}
