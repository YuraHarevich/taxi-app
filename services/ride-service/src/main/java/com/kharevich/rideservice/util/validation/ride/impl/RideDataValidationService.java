package com.kharevich.rideservice.util.validation.ride.impl;

import com.kharevich.rideservice.exception.DriverIsBusyException;
import com.kharevich.rideservice.exception.UpdateNotAllowedException;
import com.kharevich.rideservice.model.Ride;
import com.kharevich.rideservice.model.enumerations.RideStatus;
import com.kharevich.rideservice.repository.RideRepository;
import com.kharevich.rideservice.util.constants.RideServiceResponseConstants;
import com.kharevich.rideservice.util.validation.ride.RideDataValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public void checkIfDriverIsNotBusy(UUID driverId) {
        Optional<Ride> optionalRide = rideRepository.findByDriverIdAndRideStatusIn(driverId, List.of(RideStatus.ACCEPTED,RideStatus.ON_THE_WAY));
        if(optionalRide.isPresent()){
            throw new DriverIsBusyException(RideServiceResponseConstants.DRIVER_IS_BUSY);
        }
    }

}
