package com.kharevich.rideservice.client.geolocation;

import com.kharevich.rideservice.controller.exceptions.RetreiveMessageErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "geolocation-client",
        url = "${passenger-driver-geolocation-services.wire-mock.url}"
)
@Profile("test")
public interface GeolocationClientTest extends GeolocationClient {
}
