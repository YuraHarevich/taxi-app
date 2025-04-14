package ru.kharevich.authenticationservice.utils.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.kharevich.authenticationservice.dto.request.RegistrationRequest;
import ru.kharevich.authenticationservice.dto.request.UserRequest;
import ru.kharevich.authenticationservice.dto.response.UserResponse;
import ru.kharevich.authenticationservice.model.Person;
import ru.kharevich.authenticationservice.model.User;

import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserExternalPersonMapper {

    @Mapping(source = "firstname", target = "name")
    @Mapping(source = "lastname", target = "surname")
    UserRequest toUserRequest(RegistrationRequest request);

    UserResponse toUserResponse(User user);

    User toUser(Person person, UUID keycloakId, UserResponse response);

}