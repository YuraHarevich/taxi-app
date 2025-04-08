package com.Harevich.driverservice.controller;

import com.Harevich.driverservice.dto.request.UserRequest;
import com.Harevich.driverservice.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name = "Driver api for auth service")
public interface AuthDriverApi {

    public UserResponse createDriverAuth (@Valid @RequestBody UserRequest request);

    public UserResponse updateDriverAuth (@Valid @RequestBody UserRequest request, @PathVariable UUID id);

    public void deleteUserAuth (@PathVariable UUID id);

}
