package com.Harevich.ride_service.util.validation.ride;

import com.Harevich.ride_service.model.Ride;
import com.Harevich.ride_service.model.enumerations.RideStatus;

import java.util.UUID;


public interface RideDataValidation {

    Ride findIfExistsByRideId(UUID id);

    Ride findIfExistsByRideIdAndStatusIsCreated(UUID id);

    Ride findIfDriverIsNotBusy(UUID driverId);
}
