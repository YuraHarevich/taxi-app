package com.Harevich.rideservice.util.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.urls.openrouteservice")
@Getter
@Setter
public class GeolocationParams {

    private String directionsRelationalUrl;

    private String geocodeRelationalUrl;

    private String apiKey;

    private double longitude;

    private double latitude;

    private int radius;

}
