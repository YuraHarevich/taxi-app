package ru.kharevich.authenticationservice.controller.ex;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.xml.bind.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.kharevich.authenticationservice.exceptions.ExternalValidationException;
import ru.kharevich.authenticationservice.exceptions.RepeatedDataException;

import java.io.IOException;
import java.util.Map;

import static ru.kharevich.authenticationservice.utils.constants.AuthenticationServiceResponseConstants.UNKNOWN_EXTERNAL_EXCEPTION_CAUSE;

@Slf4j
public class ExternalUserErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        String causeMessage = extractExceptionMessage(response);

        switch(response.status()) {
            case 400:
                return new ExternalValidationException(causeMessage);
            case 409:
                return new RepeatedDataException(causeMessage);
        }
        return new RuntimeException();
    }

    private final String extractExceptionMessage(Response response){
        String responseBody = "";
        try {
            if (response.body() != null) {
                responseBody = Util.toString(response.body().asReader());
                log.error("Feign error: status={}, reason={}, responseBody={}",
                        response.status(), response.reason(), responseBody);

                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> responseMap = mapper.readValue(responseBody, Map.class);
                return responseMap.containsKey("message") ? (String) responseMap.get("message") : UNKNOWN_EXTERNAL_EXCEPTION_CAUSE;
            }
        } catch (IOException e) {
            log.error("Failed to read response body", e);
        }
        return UNKNOWN_EXTERNAL_EXCEPTION_CAUSE;
    }

}
