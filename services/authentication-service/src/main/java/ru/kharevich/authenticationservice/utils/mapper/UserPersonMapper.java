package ru.kharevich.authenticationservice.utils.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.kharevich.authenticationservice.dto.request.ExternalPersonRequest;
import ru.kharevich.authenticationservice.dto.request.RegistrationRequest;

import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserPersonMapper {

    ExternalPersonRequest toPersonRequest(UUID id, RegistrationRequest request);

}