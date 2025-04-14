package com.Harevich.passengerservice.controller;

import com.Harevich.passengerservice.dto.UserRequest;
import com.Harevich.passengerservice.dto.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name = "Driver api for auth service")
public interface AuthPassengerApi {

    public UserResponse createDriverAuth (@Valid @RequestBody UserRequest request);

    public UserResponse updateDriverAuth (@Valid @RequestBody UserRequest request, @PathVariable UUID id);

    public void deleteUserAuth (@PathVariable UUID id);

}
