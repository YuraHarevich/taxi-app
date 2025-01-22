package com.Harevich.passenger_service.controller;

import com.Harevich.passenger_service.dto.PassengerRequest;
import com.Harevich.passenger_service.dto.PassengerResponse;
import com.Harevich.passenger_service.util.mapper.PassengerMapper;
import com.Harevich.passenger_service.service.PassengerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/passenger")
public class PassengerController {
    private final PassengerService passengerService;
    @PostMapping("registration")
    @ResponseStatus(HttpStatus.CREATED)
    public PassengerResponse registration(@Valid @RequestBody PassengerRequest request){
        return PassengerMapper.toResponse(passengerService.registrate(request));
    }

    @PatchMapping("edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PassengerResponse edit(@RequestParam("id") UUID id,@Valid @RequestBody PassengerRequest request){
        return PassengerMapper.toResponse(passengerService.edit(request,id));
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
