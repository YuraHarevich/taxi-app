package com.Harevich.passengerservice.controller.impl;

import com.Harevich.passengerservice.controller.PassengerApi;
import com.Harevich.passengerservice.dto.PassengerRequest;
import com.Harevich.passengerservice.dto.PassengerResponse;
import com.Harevich.passengerservice.service.PassengerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/passengers")
@Tag(name = "Passenger api",
        description = "This controller is made to communicate with passenger service")
public class PassengerControllerImpl implements PassengerApi {
    private final PassengerService passengerService;

    @PostMapping("registration")
    @ResponseStatus(HttpStatus.CREATED)
    public PassengerResponse registration(@Valid @RequestBody PassengerRequest request){
        return passengerService.registrate(request);
    }

    @PatchMapping("edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PassengerResponse edit(@RequestParam("id") UUID id,@Valid @RequestBody PassengerRequest request){
        return passengerService.edit(request,id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PassengerResponse getPassengerById(@RequestParam("id") UUID id){
        return passengerService.getById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deletePassengerById(@RequestParam("id") UUID id){
        passengerService.deleteById(id);
    }
}
