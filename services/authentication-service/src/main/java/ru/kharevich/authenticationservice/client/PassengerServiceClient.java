package ru.kharevich.authenticationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kharevich.authenticationservice.controller.ex.ExternalUserErrorDecoder;
import ru.kharevich.authenticationservice.dto.request.UserRequest;
import ru.kharevich.authenticationservice.dto.response.UserResponse;
import ru.kharevich.authenticationservice.utils.config.FeignConfig;

@FeignClient(
        name = "passenger-service",
        configuration = {ExternalUserErrorDecoder.class,  FeignConfig.class}
)
public interface PassengerServiceClient {

    @PostMapping("api/v1/passengers/user")
    UserResponse createPassenger(@RequestBody UserRequest request);

}