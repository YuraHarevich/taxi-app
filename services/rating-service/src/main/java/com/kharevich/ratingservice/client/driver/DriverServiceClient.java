package com.kharevich.ratingservice.client.driver;

import com.kharevich.ratingservice.sideservices.driver.DriverResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface DriverServiceClient {

    @GetMapping
    DriverResponse getDriverIfExists(@RequestParam("id") UUID id);

}
