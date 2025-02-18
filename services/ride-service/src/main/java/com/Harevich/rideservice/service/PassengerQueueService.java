package com.Harevich.rideservice.service;

import com.Harevich.rideservice.dto.request.RideRequest;
import com.Harevich.rideservice.model.queue.PassengerQueueElement;

import java.util.UUID;

public interface PassengerQueueService {

    public void addElement(UUID passengerId, RideRequest request);

    public PassengerQueueElement getElement();

}
