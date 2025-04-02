package com.kharevich.rideservice.client.driver;

import com.kharevich.rideservice.controller.exceptions.DriverServiceErrorDecoder;
import com.kharevich.rideservice.util.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "driver-service",
        configuration = {DriverServiceErrorDecoder.class, FeignConfig.class}
)
public interface DriverServiceClientDev extends DriverServiceClient {
}
