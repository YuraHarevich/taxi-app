package com.kharevich.rideservice.client.passenger;

import com.kharevich.rideservice.controller.exceptions.PassengerServiceErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "passenger-service-client",
        configuration = PassengerServiceErrorDecoder.class
)
@Profile("dev")
public interface PassengerServiceClientDev {
}
