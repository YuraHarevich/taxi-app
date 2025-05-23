package ru.kharevich.authenticationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kharevich.authenticationservice.dto.request.RegistrationRequest;
import ru.kharevich.authenticationservice.dto.request.UserLoginRequest;
import ru.kharevich.authenticationservice.dto.request.UserRequest;
import ru.kharevich.authenticationservice.dto.response.RegistrationResponse;
import ru.kharevich.authenticationservice.dto.response.UserResponse;

import java.util.UUID;

@Tag(name = "User api")
public interface UserApi {

    @Operation(summary = "creating user for driver")
    public UserResponse createUserDriver(@Valid @RequestBody RegistrationRequest request);

    @Operation(summary = "creating user for passenger")
    public UserResponse createUserPassenger(@Valid @RequestBody RegistrationRequest request);

    @Operation(summary = "updating the user")
    public RegistrationResponse updateCurrentUser(@Valid @RequestBody RegistrationRequest request);

    @Operation(summary = "deleting of the user")
    public void deleteCurrentUser();

    @Operation(summary = "login")
    public AccessTokenResponse login(UserLoginRequest userLoginRecord);

}
