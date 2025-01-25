package com.Harevich.driverservice.controller.impl;

import com.Harevich.driverservice.dto.driver.DriverRequest;
import com.Harevich.driverservice.dto.driver.DriverResponse;
import com.Harevich.driverservice.service.DriverService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/drivers")
public class DriverController {
    private final DriverService driverService;

    @PostMapping("registration")
    @ResponseStatus(HttpStatus.CREATED)
    public DriverResponse registration(@Valid @RequestBody DriverRequest request){
        return driverService.registration(request);
    }

    @PatchMapping("edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DriverResponse edit(@RequestParam("id") UUID id, @Valid @RequestBody DriverRequest request){
        return driverService.edit(request,id);
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
}
