package com.Harevich.ride_service.service.impl;

import com.Harevich.ride_service.dto.RideRequest;
import com.Harevich.ride_service.dto.RideResponse;
import com.Harevich.ride_service.repository.RideRepository;
import com.Harevich.ride_service.service.RideService;
import com.Harevich.ride_service.util.mapper.RideMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final RideRepository rideRepository;
    private final RideMapper mapper;

    @Override
    public RideResponse createRide(RideRequest request, UUID passengerId, UUID driverId) {

        return null;
    }

    @Override
    public RideResponse updateRide(RideRequest request, UUID id) {
        return null;
    }

    @Override
    public RideResponse changeRideStatus(UUID id) {
        return null;
    }

    @Override
    public RideResponse getRideById(UUID id) {
        return null;
    }

    @Override
    public RideResponse getAllRides() {
        return null;
    }

    @Override
    public RideResponse getAllRidesByPassengerId() {
        return null;
    }

    @Override
    public RideResponse getAllRidesByDriverId() {
        return null;
    }
}
