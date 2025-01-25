package com.Harevich.driverservice.controller;

import com.Harevich.driverservice.dto.car.CarRequest;
import com.Harevich.driverservice.dto.car.CarResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Driver api",
        description = "This controller is made to communicate with cars from driver service")
public interface CarApi {
    public CarResponse create(@RequestParam("driver_id") UUID driver_id,
                              @Valid @RequestBody CarRequest request);

    public CarResponse edit(@RequestParam("id") UUID id,
                            @Valid @RequestBody CarRequest request);

    public CarResponse getCarById(@RequestParam("id") UUID id);

    public CarResponse getCarByNumber(@RequestParam("number") String number);

    public void deleteDriverById(@RequestParam("id") UUID id);
}
