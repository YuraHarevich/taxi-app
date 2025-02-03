package com.Harevich.ride_service.service;

import com.Harevich.ride_service.dto.response.PageableResponse;
import com.Harevich.ride_service.dto.request.RideRequest;
import com.Harevich.ride_service.dto.response.RideResponse;
import jakarta.validation.Valid;

import java.util.UUID;

public interface RideService {
    RideResponse createRide(@Valid RideRequest request, UUID passengerId, UUID driverId);

    RideResponse updateRide(@Valid RideRequest request, UUID id);

    RideResponse changeRideStatus(UUID id);

    RideResponse getRideById(UUID id);

    PageableResponse<RideResponse> getAllRides(int pageNumber, int size);

    PageableResponse<RideResponse> getAllRidesByPassengerId(UUID passenger_id,int pageNumber, int size);

    PageableResponse<RideResponse> getAllRidesByDriverId(UUID driver_id,int pageNumber, int size);
}
