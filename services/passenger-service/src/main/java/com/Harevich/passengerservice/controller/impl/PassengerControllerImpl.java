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
public class PassengerControllerImpl implements PassengerApi {
    private final PassengerService passengerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassengerResponse createPassenger(@Valid @RequestBody PassengerRequest request){
        return passengerService.create(request);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PassengerResponse updatePassenger(@RequestParam("id") UUID id, @Valid @RequestBody PassengerRequest request){
        return passengerService.update(request,id);
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
