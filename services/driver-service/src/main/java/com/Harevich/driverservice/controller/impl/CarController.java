package com.Harevich.driverservice.controller.impl;

import com.Harevich.driverservice.dto.car.CarRequest;
import com.Harevich.driverservice.dto.car.CarResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/cars")
public class CarController {
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponse create(@Valid @RequestBody CarRequest request){

    }

    public CarResponse edit(@RequestParam("id") UUID id, @Valid @RequestBody CarRequest request){

    }

    public CarResponse getCarById(@RequestParam("id") UUID id){

    }

    public CarResponse getCarByNumber(@RequestParam("number") String number){

    }

    public void deleteDriverById(@RequestParam("id") UUID id){

    }
}
