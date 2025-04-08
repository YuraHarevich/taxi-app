package ru.kharevich.authenticationservice.service;

import jakarta.validation.Valid;
import org.keycloak.representations.AccessTokenResponse;
import ru.kharevich.authenticationservice.dto.request.RegistrationRequest;
import ru.kharevich.authenticationservice.dto.request.UserLoginRequest;
import ru.kharevich.authenticationservice.dto.request.UserRequest;
import ru.kharevich.authenticationservice.dto.response.UserResponse;
import ru.kharevich.authenticationservice.dto.response.RegistrationResponse;
import ru.kharevich.authenticationservice.model.User;

import java.util.UUID;

public interface UserService {

    RegistrationResponse createUser(@Valid  RegistrationRequest request);

    UserResponse createDriver(@Valid  RegistrationRequest request);

    UserResponse createPassenger(@Valid  RegistrationRequest request);

    RegistrationResponse updateUser(@Valid RegistrationRequest request);

    RegistrationResponse updatePerson(@Valid RegistrationRequest request);

    UUID deleteUser();

    void deletePerson();

    void deleteUserById(@Valid UUID id);

    AccessTokenResponse getJwtToken(UserLoginRequest userLoginRequest);

}
