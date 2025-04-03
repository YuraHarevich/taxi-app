package ru.kharevich.authenticationservice.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.keycloak.representations.idm.CredentialRepresentation;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserCreationProperties {

    public static final boolean userEnabledStatus = true;

    public static final boolean userEmailVerifiedStatus = false;

    public static final boolean credentialsAreTemporary = false;

    public static final String credentialsRepresentationType = CredentialRepresentation.PASSWORD;

}
