package ru.kharevich.authenticationservice.controller.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kharevich.authenticationservice.controller.UserApi;
import ru.kharevich.authenticationservice.dto.request.RegistrationRequest;
import ru.kharevich.authenticationservice.dto.request.UserLoginRequest;
import ru.kharevich.authenticationservice.dto.response.RegistrationResponse;
import ru.kharevich.authenticationservice.service.UserService;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@Slf4j
public class UserController implements UserApi {

    private final UserService userService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponse createUser(RegistrationRequest request) {
        return userService.create(request);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RegistrationResponse updateCurrentUser(RegistrationRequest request) {
        return userService.update(request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrentUser() {
        userService.delete();
    }

    @DeleteMapping("admin/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrentUser(@Valid UUID id) {
        userService.deleteById(id);
    }

    @GetMapping("jwt")
    @ResponseStatus(HttpStatus.OK)
    public AccessTokenResponse getJwt(UserLoginRequest userLoginRecord) {
        return userService.getJwtToken(userLoginRecord);
    }

}
