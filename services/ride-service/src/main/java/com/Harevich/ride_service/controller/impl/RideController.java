package com.Harevich.ride_service.controller.impl;

import com.Harevich.ride_service.controller.RideApi;
import com.Harevich.ride_service.dto.response.PageableResponse;
import com.Harevich.ride_service.dto.request.RideRequest;
import com.Harevich.ride_service.dto.response.RideResponse;
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
    public RideResponse createRide(@Valid @RequestBody RideRequest request,
                                   @RequestParam("passenger_id") UUID passenger_id,
                                   @RequestParam("driver_id") UUID driver_id) {
        RideResponse rideResponse = rideService.createRide(request,passenger_id,driver_id);
        return rideResponse;
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RideResponse updateRide(@RequestParam("id") UUID id,
                                   @Valid @RequestBody RideRequest request) {
        RideResponse rideResponse = rideService.updateRide(request,id);
        return rideResponse;
    }

    @PatchMapping("change_status")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RideResponse changeRideStatus(@RequestParam("id") UUID id) {
        RideResponse rideResponse = rideService.changeRideStatus(id);
        return rideResponse;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RideResponse getRideById(@RequestParam("id") UUID id) {
        RideResponse rideResponse = rideService.getRideById(id);
        return rideResponse;
    }

    @GetMapping("all")
    @ResponseStatus(HttpStatus.OK)
    public PageableResponse<RideResponse> getAllRides(int pageNumber, int size) {
        PageableResponse<RideResponse> rideResponse = rideService
                .getAllRides(pageNumber,size);
        return rideResponse;
    }

    @GetMapping("all/passenger")
    @ResponseStatus(HttpStatus.OK)
    public PageableResponse<RideResponse> getAllRidesByPassengerId(@RequestParam("passenger_id") UUID passenger_id,int pageNumber, int size) {
        PageableResponse<RideResponse> rideResponse = rideService
                .getAllRidesByPassengerId(passenger_id,pageNumber,size);
        return rideResponse;
    }

    @GetMapping("all/driver")
    @ResponseStatus(HttpStatus.OK)
    public PageableResponse<RideResponse> getAllRidesByDriverId(@RequestParam("driver_id") UUID driver_id,int pageNumber, int size) {
        PageableResponse<RideResponse> rideResponse = rideService
                .getAllRidesByDriverId(driver_id,pageNumber,size);
        return rideResponse;
    }
}
