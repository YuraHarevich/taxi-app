package com.Harevich.ride_service.controller.impl;

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
@RequestMapping("api/v1/test")
@Tag(name = "Test")
public class TestController {
    private final GeolocationService service;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Coordinates getCoordsByAddress(@RequestParam("address") String address) {
        Coordinates coordinates = service.getCoordinatesByAddress(address);
        return coordinates;
    }
}
