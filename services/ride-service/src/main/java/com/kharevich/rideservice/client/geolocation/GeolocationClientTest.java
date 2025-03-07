package com.kharevich.rideservice.client.geolocation;

import com.kharevich.rideservice.controller.exceptions.DriverServiceErrorDecoder;
import com.kharevich.rideservice.controller.exceptions.RetreiveMessageErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "geolocation-client-test",
        configuration = DriverServiceErrorDecoder.class
)
@Profile("test")
public interface GeolocationClientTest extends GeolocationClient {
}
