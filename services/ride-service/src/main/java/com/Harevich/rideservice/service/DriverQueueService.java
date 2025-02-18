package com.Harevich.rideservice.service;

import com.Harevich.rideservice.dto.request.RideRequest;
import com.Harevich.rideservice.model.queue.DriverQueueElement;
import com.Harevich.rideservice.model.queue.PassengerQueueElement;

import java.util.UUID;

public interface DriverQueueService {

    public void addElement(UUID driverId);

    public DriverQueueElement getElement();

}
