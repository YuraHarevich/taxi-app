package com.kharevich.rideservice.client.driver;

import com.kharevich.rideservice.controller.exceptions.DriverServiceErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "driver-service",
        configuration = DriverServiceErrorDecoder.class
)
public interface DriverServiceClientDev extends DriverServiceClient {
}
