package ru.kharevich.authenticationservice.dto.request;

public record UserLoginRequest(

        String username,

        String password

) {
}
