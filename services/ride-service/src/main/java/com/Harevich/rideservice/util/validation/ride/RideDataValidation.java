package com.Harevich.rideservice.util.validation.ride;

import com.Harevich.rideservice.model.Ride;

import java.util.UUID;


public interface RideDataValidation {

    Ride findIfExistsByRideId(UUID id);

    Ride findIfExistsByRideIdAndStatusIsCreated(UUID id);

    void checkIfDriverIsNotBusy(UUID driverId);
}
