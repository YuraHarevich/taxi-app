package com.Harevich.passenger_service.controller.impl;

import com.Harevich.passenger_service.controller.PassengerApi;
import com.Harevich.passenger_service.dto.ErrorMessage;
import com.Harevich.passenger_service.dto.PassengerRequest;
import com.Harevich.passenger_service.dto.PassengerResponse;
import com.Harevich.passenger_service.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
