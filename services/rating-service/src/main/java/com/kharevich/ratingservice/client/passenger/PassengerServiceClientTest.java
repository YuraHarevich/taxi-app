package com.kharevich.ratingservice.client.passenger;

import com.kharevich.ratingservice.controller.exceptions.PassengerServiceErrorDecoder;
import com.kharevich.ratingservice.controller.exceptions.RideServiceErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "ride-service-client-test",
        configuration = PassengerServiceErrorDecoder.class
)
@Profile("test")
public interface PassengerServiceClientTest extends PassengerServiceClient{
}
