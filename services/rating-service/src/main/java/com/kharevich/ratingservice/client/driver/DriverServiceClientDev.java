package com.kharevich.ratingservice.client.driver;

import com.kharevich.ratingservice.controller.exceptions.DriverServiceErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "driver-service-client-dev",
        configuration = DriverServiceErrorDecoder.class
)
@Profile("dev")
public interface DriverServiceClientDev extends DriverServiceClient{
}
