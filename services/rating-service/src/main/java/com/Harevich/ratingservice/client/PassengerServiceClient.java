package com.Harevich.ratingservice.client;

import com.Harevich.ratingservice.controller.exceptions.PassengerServiceErrorDecoder;
import com.Harevich.ratingservice.sideservices.passenger.PassengerResponse;
import com.Harevich.ratingservice.util.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

@FeignClient(
        name = "passenger-service-client",
        url = "${app.urls.services.passenger-service-url}",
        configuration = {FeignConfig.class, PassengerServiceErrorDecoder.class}
)
public interface PassengerServiceClient {

    @GetMapping
    PassengerResponse getPassengerIfExists(@RequestParam("id") UUID id);

}
