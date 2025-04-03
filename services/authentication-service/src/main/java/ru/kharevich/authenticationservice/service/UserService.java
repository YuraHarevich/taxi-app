package ru.kharevich.authenticationservice.service;

import jakarta.validation.Valid;
import org.keycloak.representations.AccessTokenResponse;
import ru.kharevich.authenticationservice.dto.request.RegistrationRequest;
import ru.kharevich.authenticationservice.dto.request.UserLoginRequest;
import ru.kharevich.authenticationservice.dto.response.RegistrationResponse;

import java.util.UUID;

public interface UserService {

    RegistrationResponse create(@Valid  RegistrationRequest request);

    RegistrationResponse update(@Valid RegistrationRequest request);

    void delete();

    void deleteById(@Valid UUID id);

    AccessTokenResponse getJwtToken(UserLoginRequest userLoginRequest);

}
