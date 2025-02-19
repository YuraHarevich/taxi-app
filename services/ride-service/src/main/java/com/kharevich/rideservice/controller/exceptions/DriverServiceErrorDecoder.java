package com.kharevich.rideservice.controller.exceptions;

import com.kharevich.rideservice.exception.DriverServiceInternalErrorException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.kharevich.rideservice.util.constants.RideServiceResponseConstants.DRIVER_NOT_FOUND;
import static com.kharevich.rideservice.util.constants.RideServiceResponseConstants.DRIVER_SERVICE_UNAVAILABLE;
@Slf4j
public class DriverServiceErrorDecoder implements ErrorDecoder {
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
            return new EntityNotFoundException(DRIVER_NOT_FOUND);
        }
        return new DriverServiceInternalErrorException(DRIVER_SERVICE_UNAVAILABLE);
    }
}
