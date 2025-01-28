package com.Harevich.driverservice.controller.impl;

import com.Harevich.driverservice.controller.CarApi;
import com.Harevich.driverservice.dto.car.CarRequest;
import com.Harevich.driverservice.dto.car.CarResponse;
import com.Harevich.driverservice.service.CarService;
import com.Harevich.driverservice.service.DriverService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cars")
public class CarController implements CarApi {
    private final CarService carService;
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponse assignNewCar(@Valid @RequestBody CarRequest request,UUID driver_id){
        return carService.createNewCar(request,driver_id);
    }

    public CarResponse update(@RequestParam("id") UUID id, @Valid @RequestBody CarRequest request){
        return carService.updateCar(request,id);
    }

    public CarResponse getCarById(@RequestParam("id") UUID id){
        return carService.getById(id);
    }

    public CarResponse getCarByNumber(@RequestParam("number") String number){
        return carService.getByNumber(number);
    }

    public void deleteCarById(@RequestParam("id") UUID id){
        carService.deleteById(id);
    }
}
