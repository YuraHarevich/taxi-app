package com.kharevich.rideservice.service;

import com.kharevich.rideservice.dto.request.RideRequest;

import java.util.UUID;

public interface PassengerQueueService {

    public void addPassenger(RideRequest request);

    public void removePassenger(UUID queue_passenger_id);

}
