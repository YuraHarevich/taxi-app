package ru.kharevich.authenticationservice.controller.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kharevich.authenticationservice.controller.UserApi;
import ru.kharevich.authenticationservice.dto.request.RegistrationRequest;
import ru.kharevich.authenticationservice.dto.request.UserLoginRequest;
import ru.kharevich.authenticationservice.dto.request.UserRequest;
import ru.kharevich.authenticationservice.dto.response.RegistrationResponse;
import ru.kharevich.authenticationservice.dto.response.UserResponse;
import ru.kharevich.authenticationservice.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@Slf4j
public class UserController implements UserApi {

    private final UserService userService;

    @PostMapping("/registration/driver")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUserDriver(@Valid @RequestBody RegistrationRequest request) {
        return userService.createDriver(request);
    }

    @PostMapping("/registration/passenger")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUserPassenger(@Valid @RequestBody RegistrationRequest request) {
        return userService.createPassenger(request);
    }

    @PatchMapping("update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RegistrationResponse updateCurrentUser(@Valid @RequestBody RegistrationRequest request) {
        return userService.updatePerson(request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrentUser() {
        userService.deletePerson();
    }

    @DeleteMapping("admin/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrentUser(@Valid UUID id) {
        userService.deleteUserById(id);
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public AccessTokenResponse login(@Valid @RequestBody UserLoginRequest userLoginRecord) {
        return userService.getJwtToken(userLoginRecord);
    }

}
