package ru.kharevich.authenticationservice.service;

import jakarta.validation.Valid;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import ru.kharevich.authenticationservice.dto.request.RegistrationRequest;
import ru.kharevich.authenticationservice.dto.request.UserLoginRequest;
import ru.kharevich.authenticationservice.dto.response.RegistrationResponse;

import java.util.UUID;

public interface UserService {

    RegistrationResponse createUser(@Valid  RegistrationRequest request);

    void createDriver(@Valid  RegistrationRequest request);

    void createPassenger(@Valid  RegistrationRequest request);

    RegistrationResponse updateUser(@Valid RegistrationRequest request);

    void deleteUser();

    void deleteUserById(@Valid UUID id);

    AccessTokenResponse getJwtToken(UserLoginRequest userLoginRequest);

}
