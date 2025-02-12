package com.Harevich.rideservice.controller.impl;

import com.Harevich.rideservice.controller.GeolocationApi;
import com.Harevich.rideservice.dto.Coordinates;
import com.Harevich.rideservice.service.GeolocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
        double distance = service.getRouteDistanceByTwoAddresses(from, to);
        return distance;
    }

}
