package com.kharevich.rideservice.service;

import com.kharevich.rideservice.dto.Coordinates;

public interface GeolocationService {

    public double getRouteDistanceByTwoAddresses(String from, String to);

    public Coordinates getCoordinatesByAddress(String address);

}
