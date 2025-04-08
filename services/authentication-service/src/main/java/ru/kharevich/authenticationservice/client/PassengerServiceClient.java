package ru.kharevich.authenticationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kharevich.authenticationservice.controller.ex.ExternalUserErrorDecoder;
import ru.kharevich.authenticationservice.dto.request.UserRequest;
import ru.kharevich.authenticationservice.dto.response.UserResponse;
import ru.kharevich.authenticationservice.utils.config.FeignConfig;

import java.util.UUID;

@FeignClient(
        name = "passenger-service",
        configuration = {ExternalUserErrorDecoder.class,  FeignConfig.class}
)
public interface PassengerServiceClient {

    @PostMapping("api/v1/passengers/user/create")
    UserResponse createPassenger(@RequestBody UserRequest request);

    @PostMapping("api/v1/passengers/user/update")
    UserResponse updatePassenger(@RequestBody UserRequest request, @RequestParam("id") UUID externalId);

    @DeleteMapping("api/v1/passengers/user/delete")
    void deletePassenger(@RequestParam("id") UUID id);

}