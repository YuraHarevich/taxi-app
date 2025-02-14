package com.Harevich.driverservice.controller;

import com.Harevich.driverservice.dto.ErrorMessage;
import com.Harevich.driverservice.dto.request.DriverRequest;
import com.Harevich.driverservice.dto.response.DriverResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Driver api",
        description = "This controller is made to communicate with with drivers from driver service")
public interface DriverApi {

    @Operation(summary = "creating driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Driver successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "This number/email is already in use",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public DriverResponse create(@Valid @RequestBody DriverRequest request);

    @Operation(summary = "changing the driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Driver successfully edited"),
            @ApiResponse(responseCode = "400", description = "Invalid data format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "This number/email is already in use",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public DriverResponse update(@RequestParam("id") UUID id,
                                 @Valid @RequestBody DriverRequest request);

    @Operation(summary = "getting the driver by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Driver successfully found"),
            @ApiResponse(responseCode = "404", description = "Driver not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public DriverResponse getDriverById(@RequestParam("id") UUID id);

    @Operation(summary = "deleting driver by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Driver successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Driver not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public void deleteDriverById(@RequestParam("id") UUID id);

    @Operation(summary = "changing drivers car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Drivers car successfully changed"),
            @ApiResponse(responseCode = "404", description = "Driver not found/Car not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public DriverResponse assignPersonalCar(@RequestParam("driver_id") UUID driverId,
                                            @RequestParam("car_id") UUID carId);

}
