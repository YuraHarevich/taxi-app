package com.Harevich.ride_service.controller.impl;

import com.Harevich.ride_service.controller.RideApi;
import com.Harevich.ride_service.dto.RideRequest;
import com.Harevich.ride_service.dto.RideResponse;
import com.Harevich.ride_service.service.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rides")
public class RideController implements RideApi {
    private final RideService rideService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RideResponse createRide(@Valid RideRequest request, UUID passenger_id, UUID driver_id) {
        RideResponse rideResponse = rideService.createRide(request,passenger_id,driver_id);
        return rideResponse;
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RideResponse updateRide(UUID id,@Valid RideRequest request) {
        RideResponse rideResponse = rideService.updateRide(request,id);
        return rideResponse;
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RideResponse changeRideStatus(UUID id) {
        RideResponse rideResponse = rideService.changeRideStatus(id);
        return rideResponse;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RideResponse getRideById(UUID id) {
        RideResponse rideResponse = rideService.getRideById(id);
        return rideResponse;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageableResponse<RideResponse> getAllRides() {
        RideResponse rideResponse = rideService.getAllRides();
        return rideResponse;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageableResponse<RideResponse> getAllRidesByPassengerId(UUID passenger_id) {
        RideResponse rideResponse = rideService.getAllRidesByPassengerId();
        return rideResponse;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageableResponse<RideResponse> getAllRidesByDriverId(UUID driver_id) {
        RideResponse rideResponse = rideService.getAllRidesByDriverId();
        return rideResponse;
    }
}
