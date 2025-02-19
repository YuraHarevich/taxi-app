package com.kharevich.rideservice.client;

import com.kharevich.rideservice.controller.exceptions.DriverServiceErrorDecoder;
import com.kharevich.rideservice.sideservices.driver.DriverResponse;
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
