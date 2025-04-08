package ru.kharevich.authenticationservice.utils.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthenticationServiceResponseConstants {

    public static final String JWT_CONVERT_EXCEPTION_MESSAGE = "jwt token could not be converter";

    public static final String USER_REPEATED_DATA = "user with such username or email already exists";

    public static final String USER_CREATION_ERROR = "error while creating the user";

    public static final String AUTH_ERROR_MESSAGE = "authentication exception";

    public static final String UNKNOWN_EXTERNAL_EXCEPTION_CAUSE = "unknown exception cause";

    public static final String PERSON_ENUM_CONVERTION_EXCEPTION = "error while converting person type";

    public static final String USER_NOT_FOUND = "user with sucj id not found";

}
