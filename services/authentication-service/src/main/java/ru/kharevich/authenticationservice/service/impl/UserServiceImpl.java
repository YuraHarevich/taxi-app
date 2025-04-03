package ru.kharevich.authenticationservice.service.impl;

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
import ru.kharevich.authenticationservice.config.UserCreationProperties;
import ru.kharevich.authenticationservice.dto.request.RegistrationRequest;
import ru.kharevich.authenticationservice.dto.request.UserLoginRequest;
import ru.kharevich.authenticationservice.dto.response.RegistrationResponse;
import ru.kharevich.authenticationservice.exceptions.RepeatedUserData;
import ru.kharevich.authenticationservice.exceptions.UserCreationException;
import ru.kharevich.authenticationservice.service.UserService;
import ru.kharevich.authenticationservice.utils.constants.KeycloakProperties;

import javax.naming.AuthenticationException;
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

    @Override
    public RegistrationResponse create(RegistrationRequest request) {

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

        if(Objects.equals(201,response.getStatus())){

            List<UserRepresentation> representationList = usersResource.searchByUsername(request.username(), true);
            if(!CollectionUtils.isEmpty(representationList)){
                UserRepresentation userRepresentation1 = representationList.stream().filter(userRepresentation -> Objects.equals(false, userRepresentation.isEmailVerified())).findFirst().orElse(null);
                assert userRepresentation1 != null;
                emailVerification(userRepresentation1.getId());
                return new RegistrationResponse(
                        UUID.fromString(userRepresentation1.getId()),
                        request.username(),
                        request.firstname(),
                        request.lastname(),
                        request.email());
            }
            return null;
        }

        switch (response.getStatus()){
            case 201:
                List<UserRepresentation> representationList = usersResource.searchByUsername(request.username(), true);
                if(!CollectionUtils.isEmpty(representationList)){
                    UserRepresentation userRepresentation1 = representationList.stream().filter(userRepresentation -> Objects.equals(false, userRepresentation.isEmailVerified())).findFirst().orElse(null);
                    assert userRepresentation1 != null;
                    emailVerification(userRepresentation1.getId());
                    //assign role
                    UserResource userResourceRole = getUsersResource().get(userRepresentation1.getId());
                    RolesResource rolesResource = getRolesResource();
                    RoleRepresentation representation = rolesResource.get("USER").toRepresentation();
                    userResourceRole.roles().realmLevel().add(Collections.singletonList(representation));

                    return new RegistrationResponse(
                            UUID.fromString(userRepresentation1.getId()),
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
    public RegistrationResponse update(RegistrationRequest request) {
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

        userResource.get(userId).update(user);

        emailVerification(user.getId());

        return new RegistrationResponse(UUID.fromString(userId), request.username(), request.firstname(), request.lastname(), request.email());
    }

    @Override
    public void delete() {
        String userId = extractUserIdFromSecurityContext().orElseThrow(() -> {
            log.error("UserService.Unknown exception while extracting user id from security context");
            return new RuntimeException(AUTH_ERROR_MESSAGE);
        });
        getUsersResource().delete(userId);
    }

    @Override
    public void deleteById(UUID id) {
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

}
