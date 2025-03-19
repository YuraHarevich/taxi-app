package com.kharevich.rideservice.client.driver;

import com.kharevich.rideservice.sideservices.driver.DriverResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface DriverServiceClient {

    @GetMapping("/api/v1/drivers")
    DriverResponse getDriverIfExists(@RequestParam("id") UUID id);

}
