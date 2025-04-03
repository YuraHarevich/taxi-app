package com.Harevich.driverservice.client.auth;

import com.Harevich.driverservice.controller.ex.AuthServiceErrorDecoder;
import com.Harevich.driverservice.dto.request.auth.RegistrationRequest;
import com.Harevich.driverservice.dto.request.auth.UserLoginRequest;
import com.Harevich.driverservice.dto.response.auth.RegistrationResponse;
import com.Harevich.driverservice.util.config.FeignConfig;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "authentication-service",
        configuration = {AuthServiceErrorDecoder.class, FeignConfig.class}
)
public interface AuthenticationClient {

    @PostMapping("/api/v1/users/registration")
    RegistrationResponse registrationOfUser(@RequestBody RegistrationRequest request);

    @GetMapping("/api/v1/users/jwt")
    AccessTokenResponse login(@RequestBody UserLoginRequest request);

    @DeleteMapping("/api/v1/users")
    void deleteUser(@RequestBody UserLoginRequest request);

    @PatchMapping("/api/v1/users")
    RegistrationResponse updateUser(@RequestBody RegistrationRequest request);

}
