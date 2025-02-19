package com.Harevich.driverservice.controller.impl;

import com.Harevich.driverservice.controller.CarApi;
import com.Harevich.driverservice.dto.request.CarRequest;
import com.Harevich.driverservice.dto.response.CarResponse;
import com.Harevich.driverservice.dto.response.PageableResponse;
import com.Harevich.driverservice.service.CarService;
import com.Harevich.driverservice.util.constants.RegularExpressionConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public CarResponse createNewCar(@Valid @RequestBody CarRequest request){
        return carService.createNewCar(request);
    }

    @PatchMapping("update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CarResponse updateCar(@RequestParam("id") UUID id,
                                 @Valid @RequestBody CarRequest request){
        return carService.updateCar(request,id);
    }

    @GetMapping("id")
    @ResponseStatus(HttpStatus.OK)
    public CarResponse getCarById(@RequestParam("id") UUID id){
        return carService.getCarById(id);
    }

    @GetMapping("number")
    @ResponseStatus(HttpStatus.OK)
    public CarResponse getCarByNumber(@RequestParam("number") @Pattern(regexp = RegularExpressionConstants.CAR_NUMBER_REGEX) String number){
        return carService.getCarByNumber(number);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteCarById(@RequestParam("id") UUID id){
        carService.deleteCarById(id);
    }

    @GetMapping("available")
    @ResponseStatus(HttpStatus.OK)
    public PageableResponse<CarResponse> getAllAvailableCars(@RequestParam(defaultValue = "0") @Min(0) int page_number,
                                                             @RequestParam(defaultValue = "10") int size) {
        return carService.getAllAvailableCars(page_number,size);
    }
}
