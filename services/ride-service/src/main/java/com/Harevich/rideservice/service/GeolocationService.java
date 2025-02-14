package com.Harevich.rideservice.service;

import com.Harevich.rideservice.dto.Coordinates;

public interface GeolocationService {

    public double getRouteDistanceByTwoAddresses(String from, String to);

    public Coordinates getCoordinatesByAddress(String address);

}
