package com.Harevich.ride_service.util.validation.ride;

import com.Harevich.ride_service.model.Ride;
import org.springframework.stereotype.Service;

import java.util.UUID;


public interface RideDataValidation {
    Ride findIfExistsByRideId(UUID id);
}
