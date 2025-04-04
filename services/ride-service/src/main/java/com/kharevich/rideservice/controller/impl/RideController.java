package com.kharevich.rideservice.controller.impl;

import com.kharevich.rideservice.controller.RideApi;
import com.kharevich.rideservice.dto.response.PageableResponse;
import com.kharevich.rideservice.dto.request.RideRequest;
import com.kharevich.rideservice.dto.response.RideResponse;
import com.kharevich.rideservice.service.RideService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rides")
@Slf4j
public class RideController implements RideApi {

    private final RideService rideService;

    @PostMapping("order/driver")
    @ResponseStatus(HttpStatus.OK)
    public void applyForDriver(@RequestParam("driver_id") UUID driverId){
        rideService.applyForDriver(driverId);
    }

    @PostMapping("order/passenger")
    @ResponseStatus(HttpStatus.OK)
    public void createOrder(@Valid @RequestBody RideRequest request){
        rideService.sendRideRequest(request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RideResponse createRide(@Valid @RequestBody RideRequest request,
                                   @RequestParam("driver_id") UUID driverId) {
        RideResponse rideResponse = rideService.createRide(request, driverId);
        return rideResponse;
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RideResponse updateRide(@RequestParam("id") UUID id,
                                   @Valid @RequestBody RideRequest request) {
        RideResponse rideResponse = rideService.updateRide(request, id);
        return rideResponse;
    }

    @PatchMapping("change-status")
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
    public PageableResponse<RideResponse> getAllRides(@RequestParam(defaultValue = "0") @Min(0) int pageNumber,
                                                      @RequestParam(defaultValue = "10") int size) {
        PageableResponse<RideResponse> rideResponse = rideService
                .getAllRides(pageNumber, size>50?50:size);
        return rideResponse;
    }

    @GetMapping("all/passenger")
    @ResponseStatus(HttpStatus.OK)
    public PageableResponse<RideResponse> getAllRidesByPassengerId(@RequestParam("passenger_id") UUID passengerId,
                                                                   @RequestParam(defaultValue = "0") @Min(0) int pageNumber,
                                                                   @RequestParam(defaultValue = "10") int size) {
        PageableResponse<RideResponse> rideResponse = rideService
                .getAllRidesByPassengerId(passengerId, pageNumber, size > 50 ? 50 : size);
        return rideResponse;
    }

    @GetMapping("all/driver")
    @ResponseStatus(HttpStatus.OK)
    public PageableResponse<RideResponse> getAllRidesByDriverId(@RequestParam("driver_id") UUID driverId,
                                                                @RequestParam(defaultValue = "0") @Min(0) int pageNumber,
                                                                @RequestParam(defaultValue = "10") int size) {
        PageableResponse<RideResponse> rideResponse = rideService
                .getAllRidesByDriverId(driverId, pageNumber, size > 50 ? 50 : size);
        return rideResponse;
    }

}
