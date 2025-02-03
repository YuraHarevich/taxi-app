package com.Harevich.ride_service.service.impl;

import com.Harevich.ride_service.client.GeolocationClient;
import com.Harevich.ride_service.dto.Coordinates;
import com.Harevich.ride_service.service.GeolocationService;
import com.Harevich.ride_service.util.config.GeolocationParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeolocationServiceImpl implements GeolocationService {
    private final GeolocationParams geolocationParams;
    private final GeolocationClient geolocationClient;

    @Override
    public long getRouteLength() {
        return 0;
    }

    public Coordinates getCoordinatesByAddress(String address) {
        String relationalPath = geolocationParams.getGeocodeRelationalUrl();
        Map<String,Object> response = geolocationClient.getCoordinatesByAddress(
                relationalPath,
                geolocationParams.getApiKey(),
                geolocationParams.getFormattedAddress(address),
                geolocationParams.getCountry());
        Coordinates coordinates = getCoordinatesByOpenrouteServiceResponse(response);
        return coordinates;
    }
    private Coordinates getCoordinatesByOpenrouteServiceResponse(Map<String,Object> response){
        List<Map<String, Object>> features = (List<Map<String, Object>>)response.get("features");
        Map<String, Object> geometry = (Map<String, Object>)features.get(0).get("geometry");
        List<Double> coordinates = (List<Double>)geometry.get("coordinates");

        return new Coordinates(
                coordinates.get(0),
                coordinates.get(1)
        );
    }
}
