package com.kharevich.rideservice.controller;

import com.kharevich.rideservice.dto.ErrorMessage;
import com.kharevich.rideservice.dto.response.PageableResponse;
import com.kharevich.rideservice.dto.request.RideRequest;
import com.kharevich.rideservice.dto.response.RideResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Ride api",
        description = "This controller is made to book rides")
public interface RideApi {

    @Deprecated(forRemoval = true)
    @Operation(summary = "creating ride")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ride successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public RideResponse createRide(@Valid @RequestBody RideRequest request,
                                   @RequestParam("driver_id") UUID driverId);

    @Operation(summary = "updating the ride")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Ride successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid data format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public RideResponse updateRide(@RequestParam("id") UUID id, @Valid @RequestBody RideRequest request);

    @Operation(summary = "updating the ride status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "The ride status successfully changed")
    })
    public RideResponse changeRideStatus(@RequestParam("id") UUID id);

    @Operation(summary = "getting ride by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ride successfully found"),
            @ApiResponse(responseCode = "404", description = "Ride not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public RideResponse getRideById(@RequestParam("id") UUID id);

    @Operation(summary = "getting all rides")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ride successfully found"),
    })
    public PageableResponse<RideResponse> getAllRides(@RequestParam(defaultValue = "0") @Min(0) int pageNumber,
                                                      @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "getting ride by passengers id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ride successfully found"),
            @ApiResponse(responseCode = "404", description = "Ride not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public PageableResponse<RideResponse> getAllRidesByPassengerId(@RequestParam("id") UUID passengerId,
                                                                   @RequestParam(defaultValue = "0") @Min(0) int pageNumber,
                                                                   @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "getting ride by driver id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ride successfully found"),
            @ApiResponse(responseCode = "404", description = "Ride not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public PageableResponse<RideResponse> getAllRidesByDriverId(@RequestParam("id") UUID driverId,
                                                                @RequestParam(defaultValue = "0") @Min(0) int pageNumber,
                                                                @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "creating ride")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ready to consume orders"),
            @ApiResponse(responseCode = "400", description = "Invalid data format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public void applyForDriver(@RequestParam("driver_id") UUID driverId);

    @Operation(summary = "creating ride")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public void createOrder(@Valid @RequestBody RideRequest request);
}
