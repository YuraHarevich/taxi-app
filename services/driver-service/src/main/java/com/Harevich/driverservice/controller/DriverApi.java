package com.Harevich.driverservice.controller;

import com.Harevich.driverservice.dto.DriverRequest;
import com.Harevich.driverservice.dto.DriverResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Driver api",
        description = "This controller is made to communicate with driver service")
public interface DriverApi {

    public void registration(@Valid @RequestBody DriverRequest request);

    public DriverResponse edit(@RequestParam("id") Long id, @Valid @RequestBody DriverRequest request);

    public DriverResponse getPassengerById(@RequestParam("id") Long id);
}
