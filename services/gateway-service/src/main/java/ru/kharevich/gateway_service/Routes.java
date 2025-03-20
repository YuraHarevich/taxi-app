package ru.kharevich.gateway_service;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class Routes {
    @Bean
    public RouteLocator passengerServiceRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("passenger_service", r -> r
                        .path("/api/v1/passengers/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("passengerServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallbackRoute")
                                )
                        )
                        .uri("lb://PASSENGER-SERVICE")
                )
                .build();
    }

    @Bean
    public RouteLocator fallbackRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("fallbackRoute", r -> r
                        .path("/fallbackRoute")
                        .filters(f -> f
                                .setStatus(HttpStatus.SERVICE_UNAVAILABLE)
                        )
                        .uri("no://op")
                )
                .build();
    }

}
