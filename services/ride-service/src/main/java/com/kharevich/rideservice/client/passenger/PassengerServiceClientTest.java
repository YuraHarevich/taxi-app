package com.kharevich.rideservice.client.passenger;

import com.kharevich.rideservice.controller.exceptions.DriverServiceErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "passenger-service-client-test",
        configuration = DriverServiceErrorDecoder.class
)
@Profile("test")
public interface PassengerServiceClientTest extends PassengerServiceClient{
}
