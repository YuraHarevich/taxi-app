package com.Harevich.ride_service.service;

import com.Harevich.ride_service.dto.Coordinates;

public interface GeolocationService {
    public long getRouteLength();
    public Coordinates getCoordinatesByAddress(String address);
}
