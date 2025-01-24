package com.Harevich.driverservice.controller;

import com.Harevich.driverservice.dto.DriverRequest;
import com.Harevich.driverservice.dto.DriverResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Driver api",
        description = "This controller is made to communicate with with drivers from driver service")
public interface DriverApi {

    public DriverResponse registration(@Valid @RequestBody DriverRequest request);

    public DriverResponse edit(@RequestParam("id") UUID id, @Valid @RequestBody DriverRequest request);

    public DriverResponse getDriverById(@RequestParam("id") UUID id);
}
