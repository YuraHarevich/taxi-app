package com.Harevich.ratingservice.client;

import com.Harevich.ratingservice.controller.exceptions.RideServiceErrorDecoder;
import com.Harevich.ratingservice.util.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.Harevich.ratingservice.sideservices.ride.RideResponse;

import java.util.UUID;

@FeignClient(
        name = "ride-service-client",
        url = "${app.urls.services.ride-service-url}",
        configuration = {FeignConfig.class, RideServiceErrorDecoder.class}
)
public interface RideServiceClient {

    @GetMapping
    RideResponse getRideIfExists(@RequestParam("id") UUID id);

}
