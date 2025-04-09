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
    public RouteLocator driverServiceRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("driver_service", r -> r
                        .path("/api/v1/drivers/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("driverServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallbackRoute")
                                )
                        )
                        .uri("lb://DRIVER-SERVICE")
                )
                .build();
    }

    @Bean
    public RouteLocator rideServiceRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("ride_service", r -> r
                        .path("/api/v1/rides/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("rideServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallbackRoute")
                                )
                        )
                        .uri("lb://RIDE-SERVICE")
                )
                .build();
    }

    @Bean
    public RouteLocator ratingServiceRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("rating_service", r -> r
                        .path("/api/v1/ratings/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("ratingServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallbackRoute")
                                )
                        )
                        .uri("lb://RATING-SERVICE")
                )
                .build();
    }

    @Bean
    public RouteLocator authServiceRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("authentication_service", r -> r
                        .path("/api/v1/users/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("authServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallbackRoute")
                                )
                        )
                        .uri("lb://AUTHENTICATION-SERVICE")
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
