package com.Harevich.driverservice.controller.impl;

import com.Harevich.driverservice.controller.AuthDriverApi;
import com.Harevich.driverservice.dto.request.DriverRequest;
import com.Harevich.driverservice.dto.request.UserRequest;
import com.Harevich.driverservice.dto.response.DriverResponse;
import com.Harevich.driverservice.dto.response.UserResponse;
import com.Harevich.driverservice.service.DriverService;
import com.Harevich.driverservice.util.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.Harevich.driverservice.model.enumerations.Sex.MALE;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/drivers/user")
public class AuthDriverController implements AuthDriverApi {

    private final DriverService driverService;

    private final UserMapper userMapper;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createDriverAuth(@Valid @RequestBody UserRequest request){
        DriverRequest driverRequest = new DriverRequest(request.name(),
                request.surname(),
                request.email(),
                request.number(),
                MALE.toString());
        DriverResponse response = driverService.createNewDriver(driverRequest);
        return userMapper.toUserResponse(response);
    }

    @PostMapping("update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserResponse updateDriverAuth(@Valid @RequestBody UserRequest request,@RequestParam("id") UUID id) {
        DriverRequest driverRequest = new DriverRequest(request.name(),
                request.surname(),
                request.email(),
                request.number(),
                MALE.toString());
        DriverResponse response = driverService.updateDriver(driverRequest, id);
        return userMapper.toUserResponse(response);
    }

    @DeleteMapping("delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserAuth(@RequestParam("id") UUID id) {
        driverService.deleteDriverById(id);
    }

}
