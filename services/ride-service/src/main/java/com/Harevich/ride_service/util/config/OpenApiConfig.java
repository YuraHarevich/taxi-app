package com.Harevich.ride_service.util.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI rideServiceAPI() {
        return new OpenAPI()
                .info(new Info().title("Taxi api")
                        .description("This is the REST API for Taxi service to book rides")
                        .version("v0.0.1")
                        .license(new License().name("The License")));
    }

}