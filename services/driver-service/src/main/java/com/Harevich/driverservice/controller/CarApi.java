package com.Harevich.driverservice.controller;

import com.Harevich.driverservice.dto.car.CarRequest;
import com.Harevich.driverservice.dto.car.CarResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.query.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.util.UUID;

@Tag(name = "Driver api",
        description = "This controller is made to communicate with cars from driver service")
public interface CarApi {
    public CarResponse assignNewCar(@Valid @RequestBody CarRequest request,
                                    @RequestParam("driver_id") UUID driver_id);

    public CarResponse update(@RequestParam("id") UUID id,
                            @Valid @RequestBody CarRequest request);

    public CarResponse getCarById(@RequestParam("id") UUID id);


    public CarResponse getCarByNumber(@RequestParam("number") String number);

    public void deleteCarById(@RequestParam("id") UUID id);
}
