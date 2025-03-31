package com.kharevich.rideservice.client.passenger;

import com.kharevich.rideservice.controller.exceptions.PassengerServiceErrorDecoder;
import com.kharevich.rideservice.util.config.FeignConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "passenger-service",
        configuration = {PassengerServiceErrorDecoder.class, FeignConfig.class}
)
public interface PassengerServiceClientDev extends PassengerServiceClient {
}
