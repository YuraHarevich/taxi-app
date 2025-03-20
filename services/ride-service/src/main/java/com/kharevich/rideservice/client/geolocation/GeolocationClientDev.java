package com.kharevich.rideservice.client.geolocation;

import com.kharevich.rideservice.controller.exceptions.RetreiveMessageErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "geolocation-client",
        configuration = RetreiveMessageErrorDecoder.class
)
public interface GeolocationClientDev  extends GeolocationClient {
}
