package com.Harevich.ride_service.util.validation.ride.impl;

import com.Harevich.ride_service.exception.DriverIsBusyException;
import com.Harevich.ride_service.exception.UpdateNotAllowedException;
import com.Harevich.ride_service.model.Ride;
import com.Harevich.ride_service.model.enumerations.RideStatus;
import com.Harevich.ride_service.repository.RideRepository;
import com.Harevich.ride_service.util.constants.RideServiceResponseConstants;
import com.Harevich.ride_service.util.validation.ride.RideDataValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public Ride findIfExistsByRideIdAndStatusIsCreated(UUID id) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(RideServiceResponseConstants.RIDE_NOT_FOUND));
        if(ride.getRideStatus() != RideStatus.CREATED)
            throw new UpdateNotAllowedException(RideServiceResponseConstants.UPDATE_NOT_ALLOWED);
        return ride;
    }

    @Override
    public Ride findIfDriverIsNotBusy(UUID driverId) {
        return rideRepository.findByDriverIdAndRideStatusNotIn(driverId, List.of(RideStatus.FINISHED,RideStatus.DECLINED))
                .orElseThrow(() -> new DriverIsBusyException(RideServiceResponseConstants.DRIVER_IS_BUSY));
    }

}
