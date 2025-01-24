package com.Harevich.driverservice.controller.impl;

import com.Harevich.driverservice.dto.DriverRequest;
import com.Harevich.driverservice.dto.DriverResponse;
import com.Harevich.driverservice.mapper.DriverMapper;
import com.Harevich.driverservice.service.DriverService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/drivers")
public class DriverController {
    private final DriverService driverService;
    @PostMapping("registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(@Valid @RequestBody DriverRequest request){
        driverService.registrate(request);
    }

    @PatchMapping("edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DriverResponse edit(@RequestParam("id") Long id, @Valid @RequestBody DriverRequest request){
        return DriverMapper.toDriverResponse(driverService.edit(request,id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public DriverResponse getPassengerById(@RequestParam("id") Long id){
        return DriverMapper.toDriverResponse(driverService.getById(id));
    }
}
