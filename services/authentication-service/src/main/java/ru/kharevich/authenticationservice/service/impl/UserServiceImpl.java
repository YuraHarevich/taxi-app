package ru.kharevich.authenticationservice.service.impl;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.kharevich.authenticationservice.client.DriverServiceClient;
import ru.kharevich.authenticationservice.client.PassengerServiceClient;
import ru.kharevich.authenticationservice.config.UserCreationProperties;
import ru.kharevich.authenticationservice.dto.request.RegistrationRequest;
import ru.kharevich.authenticationservice.dto.request.UserLoginRequest;
import ru.kharevich.authenticationservice.dto.response.UserResponse;
import ru.kharevich.authenticationservice.dto.response.RegistrationResponse;
import ru.kharevich.authenticationservice.exceptions.ClientRightException;
import ru.kharevich.authenticationservice.exceptions.RepeatedUserData;
import ru.kharevich.authenticationservice.exceptions.UserCreationException;
import ru.kharevich.authenticationservice.model.User;
import ru.kharevich.authenticationservice.repository.UserRepository;
import ru.kharevich.authenticationservice.service.UserService;
import ru.kharevich.authenticationservice.utils.constants.KeycloakProperties;
import ru.kharevich.authenticationservice.utils.mapper.UserExternalPersonMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static ru.kharevich.authenticationservice.utils.constants.AuthenticationServiceResponseConstants.AUTH_ERROR_MESSAGE;
import static ru.kharevich.authenticationservice.utils.constants.AuthenticationServiceResponseConstants.USER_CREATION_ERROR;
import static ru.kharevich.authenticationservice.utils.constants.AuthenticationServiceResponseConstants.USER_REPEATED_DATA;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final Keycloak keycloak;

    private final KeycloakProperties keycloakProperties;

    private final DriverServiceClient driverServiceClient;

    private final PassengerServiceClient passengerServiceClient;

    private final UserExternalPersonMapper userPersonMapper;

    private final UserRepository userRepository;

    @Override
    public RegistrationResponse createUser(RegistrationRequest request) {

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(UserCreationProperties.userEnabledStatus);
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstName(request.firstname());
        user.setLastName(request.lastname());
        user.setEmailVerified(UserCreationProperties.userEmailVerifiedStatus);

        CredentialRepresentation credentialRepresentation=new CredentialRepresentation();
        credentialRepresentation.setValue(request.password());
        credentialRepresentation.setTemporary(UserCreationProperties.credentialsAreTemporary);
        credentialRepresentation.setType(UserCreationProperties.credentialsRepresentationType);

        List<CredentialRepresentation> list = new ArrayList<>();
        list.add(credentialRepresentation);
        user.setCredentials(list);

        UsersResource usersResource = getUsersResource();

        Response response = usersResource.create(user);
        String userId = null;

        switch (response.getStatus()){
            case 201:
                List<UserRepresentation> representationList = usersResource.searchByUsername(request.username(), true);
                if(!CollectionUtils.isEmpty(representationList)){
                    UserRepresentation userRepresentation1 = representationList.stream().filter(userRepresentation -> Objects.equals(false, userRepresentation.isEmailVerified())).findFirst().orElse(null);
                    assert userRepresentation1 != null;
                    userId = userRepresentation1.getId();
                    emailVerification(userId);

                    try {
                        assignRole(userId, "USER");
                    } catch (ForbiddenException exception){
                        log.error("UserService.Client don't have enough rights to assign role: {}", exception.getMessage());
                        deleteUserById(UUID.fromString(userId));
                        throw new ClientRightException(USER_CREATION_ERROR);
                    }

                    return new RegistrationResponse(
                            UUID.fromString(userId),
                            request.username(),
                            request.firstname(),
                            request.lastname(),
                            request.email());
                }
            case 409:
                throw new RepeatedUserData(USER_REPEATED_DATA);
            default:
                throw new UserCreationException(USER_CREATION_ERROR);
        }
    }

    @Override
    public UserResponse createDriver(RegistrationRequest request) {
        UUID keycloakId = createUser(request).id();
        UserResponse userResponse = driverServiceClient.createDriver(userPersonMapper.toUserRequest(request));
        User userToSave = userPersonMapper.toUser(keycloakId, userResponse);
        userRepository.save(userToSave);
        log.info("UserService.User with id {} successfully created", keycloakId);
        return userPersonMapper.toUserResponse(userToSave);
    }

    @Override
    public UserResponse createPassenger(RegistrationRequest request) {
        UUID keycloakId = createUser(request).id();
        UserResponse userResponse = driverServiceClient.createDriver(userPersonMapper.toUserRequest(request));
        User userToSave = userPersonMapper.toUser(keycloakId, userResponse);
        userRepository.save(userToSave);
        log.info("UserService.User with id {} successfully created", keycloakId);
        return userPersonMapper.toUserResponse(userToSave);
    }

    @Override
    public RegistrationResponse updateUser(RegistrationRequest request) {
        String userId = extractUserIdFromSecurityContext().orElseThrow(() -> {
            log.error("UserService.Unknown exception while extracting user id from security context");
            return new RuntimeException(AUTH_ERROR_MESSAGE);
        });
        UsersResource userResource = getUsersResource();
        List<UserRepresentation> users = userResource.list();
        UserRepresentation user = users.stream().filter(userRepresentation -> Objects.equals(userId, userRepresentation.getId())).findFirst().orElse(null);

        user.setEmail(request.email());
        user.setFirstName(request.firstname());
        user.setLastName(request.lastname());
        user.setUsername(request.username());
        try {
            userResource.get(userId).update(user);
        } catch (ClientErrorException ex) {
            log.error("UserService.Exception thrown while trying to update user");
            throw new UserCreationException(USER_REPEATED_DATA);
        }

        emailVerification(user.getId());

        return new RegistrationResponse(UUID.fromString(userId), request.username(), request.firstname(), request.lastname(), request.email());
    }

    @Override
    public void deleteUser() {
        String userId = extractUserIdFromSecurityContext().orElseThrow(() -> {
            log.error("UserService.Unknown exception while extracting user id from security context");
            return new RuntimeException(AUTH_ERROR_MESSAGE);
        });
        getUsersResource().delete(userId);
    }

    @Override
    public void deleteUserById(UUID id) {
        getUsersResource().delete(id.toString());
    }

    @Override
    public AccessTokenResponse getJwtToken(UserLoginRequest userLoginRequest) {
            Keycloak userKeycloak = KeycloakBuilder.builder()
                    .serverUrl(keycloakProperties.getAuthUrl())
                    .realm(keycloakProperties.getRealm())
                    .grantType(OAuth2Constants.PASSWORD)
                    .clientId(keycloakProperties.getClientId())
                    .clientSecret(keycloakProperties.getClientSecret())
                    .username(userLoginRequest.username())
                    .password(userLoginRequest.password())
                    .build();

            return userKeycloak.tokenManager().getAccessToken();
    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm(keycloakProperties.getRealm());
        return realm1.users();
    }

    public UserResource getUserResource(String userId){
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    private RolesResource getRolesResource(){
        return  keycloak.realm(keycloakProperties.getRealm()).roles();
    }

    private void emailVerification(String userId){
        log.info("sending email verification");
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    private Optional<String> extractUserIdFromSecurityContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return Optional.of(jwt.getSubject());
        }
        return Optional.empty();
    }

    private void assignRole(String userId, String roleName) {

        UserResource userResource = getUserResource(userId);
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(representation));

    }

}
