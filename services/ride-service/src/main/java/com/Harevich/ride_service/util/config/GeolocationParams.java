package com.Harevich.ride_service.util.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class GeolocationParams {

    @Value("${app.urls.openrouteservice.directions_relational_part}")
    private String directionsRelationalUrl;

    @Value("${app.urls.openrouteservice.geocode_relational_part}")
    private String geocodeRelationalUrl;

    @Value("${app.urls.openrouteservice.api_key}")
    private String apiKey;

    @Value("${app.urls.openrouteservice.country_ISO_code}")
    private String country;

    public String getRelationalDirectionsUrl(String start, String end) {
        return null;
        //return String.format("%s?api_key=%s&start=%s&end=%s", directions_relational_url, apiKey, start, end);
    }
    public String getFormattedAddress(String address) {
        String formattedAddress = address
                .replace(" ","%20")
                .replace(",","%2C");
        return formattedAddress;
    }
}
