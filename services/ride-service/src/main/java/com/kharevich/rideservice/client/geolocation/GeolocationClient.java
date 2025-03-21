package com.kharevich.rideservice.client.geolocation;

import com.kharevich.rideservice.controller.exceptions.RetreiveMessageErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface GeolocationClient {

    @GetMapping("{relative_path}")
    Map<String,Object> getCoordinatesByAddressByCity(@PathVariable("relative_path") String relativePath,
                                                     @RequestParam("api_key") String apiKey,
                                                     @RequestParam("text") String text,
                                                     @RequestParam("boundary.circle.lon") double longitude,
                                                     @RequestParam("boundary.circle.lat") double latitude,
                                                     @RequestParam("boundary.circle.radius") int radius);

    @GetMapping("{relative_path}")
    Map<String,Object> getCoordinatesByAddressByCountry(@PathVariable("relative_path") String relativePath,
                                                        @RequestParam("api_key") String apiKey,
                                                        @RequestParam("text") String text,
                                                        @RequestParam("boundary.country") String country);

    @GetMapping("{relational_path}")
    Map<String,Object> getDirectionByTwoAddresses(@PathVariable("relational_path") String relationalPath,
                                                  @RequestParam("api_key") String apiKey,
                                                  @RequestParam("start") String start,
                                                  @RequestParam("end") String end);

}