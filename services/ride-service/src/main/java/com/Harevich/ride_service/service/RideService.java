package com.Harevich.ride_service.service;

import com.Harevich.ride_service.dto.RideRequest;
import com.Harevich.ride_service.dto.RideResponse;
import jakarta.validation.Valid;

import java.util.UUID;

public interface RideService {
    RideResponse createRide(@Valid RideRequest request, UUID passengerId, UUID driverId);

    RideResponse updateRide(@Valid RideRequest request, UUID id);

    RideResponse changeRideStatus(UUID id);

    RideResponse getRideById(UUID id);

    RideResponse getAllRides();

    RideResponse getAllRidesByPassengerId();

    RideResponse getAllRidesByDriverId();
}
