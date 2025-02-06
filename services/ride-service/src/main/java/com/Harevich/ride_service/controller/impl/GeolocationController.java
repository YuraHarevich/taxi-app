package com.Harevich.ride_service.controller.impl;

import com.Harevich.ride_service.controller.GeolocationApi;
import com.Harevich.ride_service.dto.Coordinates;
import com.Harevich.ride_service.dto.response.RideResponse;
import com.Harevich.ride_service.service.GeolocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/geolocation")
public class GeolocationController implements GeolocationApi {

    private final GeolocationService service;

    @GetMapping("cords")
    @ResponseStatus(HttpStatus.OK)
    public Coordinates getCoordsByAddress(@RequestParam("address") String address) {
        Coordinates coordinates = service.getCoordinatesByAddress(address);
        return coordinates;
    }

    @GetMapping("route")
    @ResponseStatus(HttpStatus.OK)
    public double getRouteByAddresses(@RequestParam("from") String from,
                                      @RequestParam("to") String to) {
        double distance = service.getRouteDistanceByTwoAddresses(from,to);
        return distance;
    }
}
