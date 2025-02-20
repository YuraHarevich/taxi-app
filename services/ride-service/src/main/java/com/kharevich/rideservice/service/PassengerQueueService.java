package com.kharevich.rideservice.service;

import com.kharevich.rideservice.dto.request.RideRequest;

public interface PassengerQueueService {

    public void addPassenger(RideRequest request);

}
