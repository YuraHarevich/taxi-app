package ru.kharevich.authenticationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kharevich.authenticationservice.controller.ex.ExternalUserErrorDecoder;
import ru.kharevich.authenticationservice.dto.request.ExternalPersonRequest;

import java.util.UUID;

@FeignClient(
        name = "passenger-service",
        configuration = {ExternalUserErrorDecoder.class/*, FeignConfig.class*/}
)
public interface PassengerServiceClient {

    @PostMapping("api/v1/drivers/create")
    void createPassenger(@RequestBody ExternalPersonRequest request);

    @PatchMapping("api/v1/drivers/update")
    void updatePassenger(@RequestBody ExternalPersonRequest request);

    @DeleteMapping("api/v1/drivers")
    void deletePassengerById(@RequestParam("id") UUID id);

}
