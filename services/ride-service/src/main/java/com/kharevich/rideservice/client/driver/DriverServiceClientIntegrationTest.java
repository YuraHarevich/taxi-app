package com.kharevich.rideservice.client.driver;

import com.kharevich.rideservice.controller.exceptions.DriverServiceErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "driver-service"
)
@Profile("it")
public interface DriverServiceClientIntegrationTest extends DriverServiceClient {
}
