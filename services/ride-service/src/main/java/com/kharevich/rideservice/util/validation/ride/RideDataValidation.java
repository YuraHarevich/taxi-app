package com.kharevich.rideservice.util.validation.ride;

import com.kharevich.rideservice.model.Ride;

import java.util.UUID;


public interface RideDataValidation {

    Ride findIfExistsByRideId(UUID id);

    Ride findIfExistsByRideIdAndStatusIsCreated(UUID id);

    void checkIfDriverIsNotBusy(UUID driverId);
}
