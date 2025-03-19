package com.kharevich.rideservice.client.passenger;

import com.kharevich.rideservice.controller.exceptions.DriverServiceErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "passenger-service"
)
@Profile("it")
public interface PassengerServiceClientIntegrationTest extends PassengerServiceClient{
}
