package ru.kharevich.gateway_service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomReactiveJwtDecoder jwtAuthDecoder;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                .pathMatchers("/api/v1/users/login").permitAll()
                .pathMatchers("/api/v1/users/registration/**").permitAll()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers("/api/v1/rides/**").hasRole("ADMIN")
                .pathMatchers("/api/v1/passengers/**").hasRole("ADMIN")
                .pathMatchers("/api/v1/drivers/**").hasRole("ADMIN")
                .anyExchange().authenticated()
        );
        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                        .jwtDecoder(jwtAuthDecoder)
                )
        );
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }

}
