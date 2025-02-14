package com.Harevich.rideservice.client;

import com.Harevich.rideservice.controller.exceptions.DriverServiceErrorDecoder;
import com.Harevich.rideservice.sideservices.driver.DriverResponse;
import com.Harevich.rideservice.util.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(
        name = "driver-service-client",
        url = "${app.urls.services.driver-service-url}",
        configuration = {FeignConfig.class, DriverServiceErrorDecoder.class}
)
public interface DriverServiceClient {

    @GetMapping
    DriverResponse getDriverIfExists(@RequestParam("id") UUID id);

}
