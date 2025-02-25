package com.kharevich.rideservice.service;

import java.util.UUID;

public interface DriverQueueService {

    public void addDriver(UUID driverId);

    public void removeDriver(UUID queue_driver_id);

}
