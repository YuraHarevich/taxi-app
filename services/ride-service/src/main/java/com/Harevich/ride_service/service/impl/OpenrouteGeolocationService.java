package com.Harevich.ride_service.service.impl;

import com.Harevich.ride_service.client.GeolocationClient;
import com.Harevich.ride_service.dto.Coordinates;
import com.Harevich.ride_service.exception.AddressNotFoundException;
import com.Harevich.ride_service.exception.GeolocationServiceBadRequestException;
import com.Harevich.ride_service.exception.GeolocationServiceUnavailableException;
import com.Harevich.ride_service.service.GeolocationService;
import com.Harevich.ride_service.util.config.GeolocationParams;
import com.Harevich.ride_service.util.constants.RideServiceResponseConstants;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenrouteGeolocationService implements GeolocationService {
    private final GeolocationParams geolocationParams;
    private final GeolocationClient geolocationClient;

    @Override
    public double getRouteDistanceByTwoAddresses(String from, String to) {
        String fromCordsString = getCoordinatesByAddress(from).toString();
        String toCordsString = getCoordinatesByAddress(to).toString();
        double distance;
        try {
            Map<String, Object> response = geolocationClient.getDirectionByTwoAddresses(
                    geolocationParams.getDirectionsRelationalUrl(),
                    geolocationParams.getApiKey(),
                    fromCordsString,
                    toCordsString);
            distance = getDistanseInKilometersByOpenrouteServiceResponse(response);
        }catch (FeignException.BadRequest e) {
            throw new GeolocationServiceBadRequestException(RideServiceResponseConstants.OUTSIDER_REST_API_BAD_REQUEST);
        }catch (FeignException e) {
            throw new GeolocationServiceUnavailableException(RideServiceResponseConstants.OUTSIDER_REST_API_BAD_UNAVAILABLE);
        }
        return distance;
    }


    public Coordinates getCoordinatesByAddress(String address) {
        String relationalPath = geolocationParams.getGeocodeRelationalUrl();
        Map<String,Object> response = geolocationClient.getCoordinatesByAddressByCity(
                relationalPath,
                geolocationParams.getApiKey(),
                address,
                geolocationParams.getMinskLongitude(),
                geolocationParams.getMinskLatitude(),
                geolocationParams.getMinskRadius());
        Coordinates coordinates = getCoordinatesByOpenrouteServiceResponse(response, address);
        return coordinates;
    }

    private Coordinates getCoordinatesByOpenrouteServiceResponse(Map<String,Object> response, String address){
        List<Map<String, Object>> features = (List<Map<String, Object>>)response.get("features");
        if(features.isEmpty())
            throw new AddressNotFoundException(RideServiceResponseConstants.ADDRESS_NOT_FOUND.formatted(address));
        Map<String, Object> geometry = (Map<String, Object>)features.get(0).get("geometry");
        List<Double> coordinates = (List<Double>)geometry.get("coordinates");
        return new Coordinates(
                coordinates.get(0),
                coordinates.get(1)
        );
    }

    private double getDistanseInKilometersByOpenrouteServiceResponse(Map<String, Object> response) {
        List<Map<String, Object>> features = (List<Map<String, Object>>)response.get("features");
        Map<String, Object> properties = (Map<String, Object>)features.get(0).get("properties");
        Map<String, Object> summary = (Map<String, Object>)properties.get("summary");
        double distance = (double)summary.get("distance");
        double distanceInKilometers = distance/1000;
        return Math.round(distanceInKilometers * 100.0)/100.0;
    }
}
