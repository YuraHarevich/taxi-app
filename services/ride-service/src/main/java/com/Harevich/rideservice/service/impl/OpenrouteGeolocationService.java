package com.Harevich.rideservice.service.impl;

import static com.Harevich.rideservice.util.constants.GeolocationResponseConstants.COORDINATES_FIRST_INNER_OBJECT_NAME;
import static com.Harevich.rideservice.util.constants.GeolocationResponseConstants.COORDINATES_INNER_LATITUDE_NUMBER;
import static com.Harevich.rideservice.util.constants.GeolocationResponseConstants.COORDINATES_INNER_LONGITUDE_NUMBER;
import static com.Harevich.rideservice.util.constants.GeolocationResponseConstants.COORDINATES_OBJECT_NAME;
import static com.Harevich.rideservice.util.constants.GeolocationResponseConstants.DISTANCE_FIRST_INNER_OBJECT_NAME;
import static com.Harevich.rideservice.util.constants.GeolocationResponseConstants.DISTANCE_OBJECT_NAME;
import static com.Harevich.rideservice.util.constants.GeolocationResponseConstants.DISTANCE_SECOND_INNER_OBJECT_NAME;
import static com.Harevich.rideservice.util.constants.GeolocationResponseConstants.KILOMETER_TO_METER_DIVIDE_CONSTANT;
import static com.Harevich.rideservice.util.constants.GeolocationResponseConstants.MAIN_OBJECT_INNER_NUMBER;
import static com.Harevich.rideservice.util.constants.GeolocationResponseConstants.MAIN_OBJECT_NAME;
import static com.Harevich.rideservice.util.constants.RideServiceResponseConstants.ADDRESS_NOT_FOUND;
import static com.Harevich.rideservice.util.constants.TariffMultiplyConstants.ACCURACY_OF_DISTANCE;

import com.Harevich.rideservice.client.GeolocationClient;
import com.Harevich.rideservice.dto.Coordinates;
import com.Harevich.rideservice.exception.AddressNotFoundException;
import com.Harevich.rideservice.service.GeolocationService;
import com.Harevich.rideservice.util.config.GeolocationParams;
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

        Map<String, Object> response = geolocationClient.getDirectionByTwoAddresses(
                geolocationParams.getDirectionsRelationalUrl(),
                geolocationParams.getApiKey(),
                fromCordsString,
                toCordsString);

        distance = getDistanseInKilometersByOpenrouteServiceResponse(response);

        return distance;
    }

    public Coordinates getCoordinatesByAddress(String address) {
        String relationalPath = geolocationParams.getGeocodeRelationalUrl();
        Map<String,Object> response = geolocationClient.getCoordinatesByAddressByCity(
                relationalPath,
                geolocationParams.getApiKey(),
                address,
                geolocationParams.getLongitude(),
                geolocationParams.getLatitude(),
                geolocationParams.getRadius());
        Coordinates coordinates = getCoordinatesByOpenrouteServiceResponse(response, address);
        return coordinates;
    }

    private Coordinates getCoordinatesByOpenrouteServiceResponse(Map<String,Object> response, String address) {
        List<Map<String, Object>> features = (List<Map<String, Object>>) response.get(MAIN_OBJECT_NAME);
        if (features.isEmpty()) {
            throw new AddressNotFoundException(ADDRESS_NOT_FOUND.formatted(address));
        }
        Map<String, Object> geometry = (Map<String, Object>) features
                .get(MAIN_OBJECT_INNER_NUMBER)
                .get(COORDINATES_FIRST_INNER_OBJECT_NAME);
        List<Double> coordinates = (List<Double>) geometry
                .get(COORDINATES_OBJECT_NAME);
        return new Coordinates(
                coordinates.get(COORDINATES_INNER_LONGITUDE_NUMBER),
                coordinates.get(COORDINATES_INNER_LATITUDE_NUMBER)
        );
    }

    private double getDistanseInKilometersByOpenrouteServiceResponse(Map<String, Object> response) {
        List<Map<String, Object>> features = (List<Map<String, Object>>) response.get(MAIN_OBJECT_NAME);
        Map<String, Object> properties = (Map<String, Object>) features
                .get(MAIN_OBJECT_INNER_NUMBER)
                .get(DISTANCE_FIRST_INNER_OBJECT_NAME);
        Map<String, Object> summary = (Map<String, Object>) properties
                .get(DISTANCE_SECOND_INNER_OBJECT_NAME);
        double distance = (double) summary
                .get(DISTANCE_OBJECT_NAME);
        double distanceInKilometers = distance / KILOMETER_TO_METER_DIVIDE_CONSTANT;
        return Math.round(distanceInKilometers * ACCURACY_OF_DISTANCE) / ACCURACY_OF_DISTANCE;
    }

}
