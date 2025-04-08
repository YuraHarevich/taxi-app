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
        name = "driver-service",
        configuration = {ExternalUserErrorDecoder.class, FeignConfig.class}
)
public interface DriverServiceClient {

    @PostMapping("api/v1/drivers/user/create")
    UserResponse createDriver(@RequestBody UserRequest request);

    @PostMapping("api/v1/drivers/user/update")
    UserResponse updateDriver(@RequestBody UserRequest request, @RequestParam("id") UUID externalId);

    @DeleteMapping("api/v1/drivers/user/delete")
    void deleteDriver(@RequestParam("id") UUID id);

}
