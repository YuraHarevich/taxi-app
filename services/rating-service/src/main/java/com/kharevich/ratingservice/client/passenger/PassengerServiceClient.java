package com.kharevich.ratingservice.client.passenger;

import com.kharevich.ratingservice.controller.exceptions.PassengerServiceErrorDecoder;
import com.kharevich.ratingservice.sideservices.passenger.PassengerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface PassengerServiceClient {

    @GetMapping("/api/v1/passengers")
    PassengerResponse getPassengerIfExists(@RequestParam("id") UUID id);

}
