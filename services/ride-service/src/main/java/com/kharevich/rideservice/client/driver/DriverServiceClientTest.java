package com.kharevich.rideservice.client.driver;

import com.kharevich.rideservice.controller.exceptions.DriverServiceErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "driver-service-client",
        url = "${passenger-driver-geolocation-services.wire-mock.url}"
)
@Profile("test")
public interface DriverServiceClientTest  extends DriverServiceClient {
}
