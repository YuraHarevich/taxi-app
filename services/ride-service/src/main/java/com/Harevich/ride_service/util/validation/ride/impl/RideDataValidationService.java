package com.Harevich.ride_service.util.validation.ride.impl;

import com.Harevich.ride_service.model.Ride;
import com.Harevich.ride_service.repository.RideRepository;
import com.Harevich.ride_service.util.constants.RideServiceResponseConstants;
import com.Harevich.ride_service.util.validation.ride.RideDataValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RideDataValidationService implements RideDataValidation {
    private final RideRepository rideRepository;
    @Override
    public Ride findIfExistsByRideId(UUID id) {
        return rideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(RideServiceResponseConstants.RIDE_NOT_FOUND));
    }
}
