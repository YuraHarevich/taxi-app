package com.Harevich.ride_service.service;

import com.Harevich.ride_service.dto.Coordinates;

public interface GeolocationService {

    public double getRouteDistanceByTwoAddresses(String from, String to);

    public Coordinates getCoordinatesByAddress(String address);

}
