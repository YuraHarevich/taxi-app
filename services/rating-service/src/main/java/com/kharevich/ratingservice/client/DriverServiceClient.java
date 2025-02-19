package com.kharevich.ratingservice.client;

import com.kharevich.ratingservice.controller.exceptions.DriverServiceErrorDecoder;
import com.kharevich.ratingservice.sideservices.driver.DriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(
        name = "driver-service-client",
        configuration = DriverServiceErrorDecoder.class
)
public interface DriverServiceClient {

    @GetMapping
    DriverResponse getDriverIfExists(@RequestParam("id") UUID id);

}
