package com.Harevich.rideservice.service;

import com.Harevich.rideservice.dto.response.PageableResponse;
import com.Harevich.rideservice.dto.request.RideRequest;
import com.Harevich.rideservice.dto.response.RideResponse;
import jakarta.validation.Valid;

import java.util.UUID;

public interface RideService {

    RideResponse createRide(RideRequest request, UUID driverId);

    RideResponse updateRide(RideRequest request, UUID id);

    RideResponse changeRideStatus(UUID id);

    RideResponse getRideById(UUID id);

    PageableResponse<RideResponse> getAllRides(int pageNumber, int size);

    PageableResponse<RideResponse> getAllRidesByPassengerId(UUID passengerId,int pageNumber, int size);

    PageableResponse<RideResponse> getAllRidesByDriverId(UUID driverId,int pageNumber, int size);

    void applyForDriver(UUID driverId);

    void sendRideRequest(@Valid RideRequest request);
}
