package com.Harevich.passengerservice.controller.impl;

import com.Harevich.passengerservice.controller.AuthPassengerApi;
import com.Harevich.passengerservice.dto.PassengerRequest;
import com.Harevich.passengerservice.dto.PassengerResponse;
import com.Harevich.passengerservice.dto.UserRequest;
import com.Harevich.passengerservice.dto.UserResponse;
import com.Harevich.passengerservice.service.PassengerService;
import com.Harevich.passengerservice.util.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/passengers/user")
public class AuthPassengerController implements AuthPassengerApi {

    private final PassengerService passengerService;

    private final UserMapper userMapper;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createDriverAuth(@Valid @RequestBody UserRequest request){
        PassengerRequest passengerRequest = new PassengerRequest(request.name(),
                request.surname(),
                request.email(),
                request.number());
        PassengerResponse response = passengerService.create(passengerRequest);
        return userMapper.toUserResponse(response);
    }

    @PostMapping("update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserResponse updateDriverAuth(@Valid @RequestBody UserRequest request,@RequestParam("id") UUID id) {
        PassengerRequest passengerRequest = new PassengerRequest(request.name(),
                request.surname(),
                request.email(),
                request.number());
        PassengerResponse response = passengerService.update(passengerRequest, id);
        return userMapper.toUserResponse(response);
    }

    @DeleteMapping("delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserAuth(@RequestParam("id") UUID id) {
        passengerService.deleteById(id);
    }

}
