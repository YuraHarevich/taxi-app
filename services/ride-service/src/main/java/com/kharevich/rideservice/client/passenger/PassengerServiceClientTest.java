package com.kharevich.rideservice.client.passenger;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "passenger-service-client",
        url = "${passenger-driver-geolocation-services.wire-mock.url}"
)
@Profile("test")
public interface PassengerServiceClientTest {
}
