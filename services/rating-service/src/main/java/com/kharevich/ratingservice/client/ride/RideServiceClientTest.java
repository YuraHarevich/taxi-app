package com.kharevich.ratingservice.client.ride;

import com.kharevich.ratingservice.controller.exceptions.RideServiceErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "ride-service-client-test",
        configuration = RideServiceErrorDecoder.class
)
@Profile("test")
public interface RideServiceClientTest extends RideServiceClient {
}
