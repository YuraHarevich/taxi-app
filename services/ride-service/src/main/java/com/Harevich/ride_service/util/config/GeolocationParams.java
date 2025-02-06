package com.Harevich.ride_service.util.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public final class GeolocationParams {

    @Value("${app.urls.openrouteservice.directions_relational_url}")
    private String directionsRelationalUrl;

    @Value("${app.urls.openrouteservice.geocode_relational_url}")
    private String geocodeRelationalUrl;

    @Value("${app.urls.openrouteservice.api_key}")
    private String apiKey;

    @Value("${app.urls.openrouteservice.country_iso_code}")
    private String country;

    @Value("${app.urls.openrouteservice.city.minsk.lon}")
    private double minskLongitude;

    @Value("${app.urls.openrouteservice.city.minsk.lat}")
    private double minskLatitude;

    @Value("${app.urls.openrouteservice.city.minsk.radius}")
    private int minskRadius;

}
