package ru.kharevich.authenticationservice.controller.ex;

import static ru.kharevich.authenticationservice.utils.constants.AuthenticationServiceResponseConstants.UNKNOWN_EXTERNAL_EXCEPTION_CAUSE;
import static ru.kharevich.authenticationservice.utils.constants.AuthenticationServiceResponseConstants.UNKNOWN_LINKED_SERVICE_ERROR_MESSAGE;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.kharevich.authenticationservice.exceptions.ExternalServiceUnavailableException;
import ru.kharevich.authenticationservice.exceptions.ExternalValidationException;
import ru.kharevich.authenticationservice.exceptions.RepeatedDataException;
import ru.kharevich.authenticationservice.service.UserService;


@Slf4j
@AllArgsConstructor
public class ExternalUserErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String causeMessage = extractExceptionMessage(response);

        return switch (response.status()) {
            case 400 -> new ExternalValidationException(causeMessage);
            case 404 -> new EntityNotFoundException(causeMessage);
            case 409 -> new RepeatedDataException(causeMessage);
            default -> new ExternalServiceUnavailableException(causeMessage);
        };
    }

    private final String extractExceptionMessage(Response response) {
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
            log.info("Driver/Passenger service is unavailable");
            throw new ExternalServiceUnavailableException(UNKNOWN_LINKED_SERVICE_ERROR_MESSAGE);
        }
        return UNKNOWN_EXTERNAL_EXCEPTION_CAUSE;
    }

}
