package com.Harevich.driverservice.controller.impl;

import com.Harevich.driverservice.controller.DriverApi;
import com.Harevich.driverservice.dto.response.CarResponse;
import com.Harevich.driverservice.dto.request.DriverRequest;
import com.Harevich.driverservice.dto.response.DriverResponse;
import com.Harevich.driverservice.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/drivers")
public class DriverController implements DriverApi {
    private final DriverService driverService;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public DriverResponse create(@Valid @RequestBody DriverRequest request){
        return driverService.createNewDriver(request);
    }

    @PatchMapping("update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DriverResponse update(@RequestParam("id") UUID id, @Valid @RequestBody DriverRequest request){
        return driverService.updateDriver(request,id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public DriverResponse getDriverById(@RequestParam("id") UUID id){
        return driverService.getById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteDriverById(@RequestParam("id") UUID id){
        driverService.deleteById(id);
    }

    @PatchMapping("changeCar")
    @ResponseStatus(HttpStatus.OK)
    public DriverResponse assignPersonalCar(@RequestParam("driver_id") UUID driver_id,
                                            @RequestParam("car_id") UUID car_id){
        return driverService.assignPersonalCar(driver_id,car_id);
    }



}
