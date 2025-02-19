package com.kharevich.rideservice.client;

import com.kharevich.rideservice.controller.exceptions.PassengerServiceErrorDecoder;
import com.kharevich.rideservice.sideservices.passenger.PassengerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(
        name = "passenger-service-client",
        configuration = PassengerServiceErrorDecoder.class
)
public interface PassengerServiceClient {

    @GetMapping
    PassengerResponse getPassengerIfExists(@RequestParam("id") UUID id);

}
