package com.kharevich.ratingservice.client.ride;

import com.kharevich.ratingservice.controller.exceptions.RideServiceErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.kharevich.ratingservice.sideservices.ride.RideResponse;

import java.util.UUID;

public interface RideServiceClient {

    @GetMapping
    RideResponse getRideIfExists(@RequestParam("id") UUID id);

}
