package com.Harevich.driverservice.controller;

import com.Harevich.driverservice.dto.ErrorMessage;
import com.Harevich.driverservice.dto.driver.DriverRequest;
import com.Harevich.driverservice.dto.driver.DriverResponse;
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

    @Operation(summary = "registration of the driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Driver successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "This number/email is already in use",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public DriverResponse registration(@Valid @RequestBody DriverRequest request);

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
    public DriverResponse edit(@RequestParam("id") UUID id, @Valid @RequestBody DriverRequest request);

    @Operation(summary = "getting the driver by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Driver successfully found"),
            @ApiResponse(responseCode = "404", description = "Driver not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public DriverResponse getDriverById(@RequestParam("id") UUID id);

    @Operation(summary = "getting the driver by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Driver successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Driver not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public void deleteDriverById(@RequestParam("id") UUID id);

}
