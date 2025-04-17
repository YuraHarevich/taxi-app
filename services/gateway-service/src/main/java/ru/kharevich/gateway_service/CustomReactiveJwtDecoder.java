package ru.kharevich.gateway_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Component
public class CustomReactiveJwtDecoder implements ReactiveJwtDecoder {

    @Override
    public Mono<Jwt> decode(String token) throws JwtException {
        return Mono.fromCallable(() -> {
            AccessToken accessToken;

            try {
                accessToken = TokenVerifier.create(token, AccessToken.class).getToken();

                Map<String, Object> claims = new HashMap<>();
                claims.put("exp", accessToken.getExp());
                claims.put("iat", accessToken.getIat());
                claims.put("sub", accessToken.getSubject());
                claims.put("preferred_username", accessToken.getPreferredUsername());
                claims.put("realm_access", accessToken.getRealmAccess());
                return new Jwt(
                        token,
                        Instant.ofEpochSecond(accessToken.getIat()),
                        Instant.ofEpochSecond(accessToken.getExp()),
                        Map.of("alg", "RS256"),
                        claims
                );
            } catch (VerificationException e) {
                log.error("Token verification failed: {}", e.getMessage());
                throw new JwtException("Invalid JWT token", e);
            }
        });
    }
}

