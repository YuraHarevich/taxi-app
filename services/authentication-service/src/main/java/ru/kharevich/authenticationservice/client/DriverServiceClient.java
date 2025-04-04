package ru.kharevich.authenticationservice.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kharevich.authenticationservice.controller.ex.ExternalUserErrorDecoder;
import ru.kharevich.authenticationservice.dto.request.ExternalPersonRequest;

import java.util.UUID;

@FeignClient(
        name = "driver-service",
        configuration = {ExternalUserErrorDecoder.class/*, FeignConfig.class*/}
)
public interface DriverServiceClient {

    @PostMapping("api/v1/drivers/create")
    void createDriver(@RequestBody ExternalPersonRequest request);

    @PatchMapping("api/v1/drivers/update")
    void updateDriver(@RequestBody ExternalPersonRequest request);

    @DeleteMapping("api/v1/drivers")
    void deleteDriverById(@RequestParam("id") UUID id);

}
