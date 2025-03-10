package com.kharevich.rideservice.controller;

import com.kharevich.rideservice.dto.Coordinates;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Geolocation api")
public interface GeolocationApi {

    @Operation(summary = "coordinates are returned")
    @ApiResponses(value = @ApiResponse(responseCode = "200"))
    public Coordinates getCoordsByAddress(@RequestParam("address") String address);

    @Operation(summary = "route distanse is returned")
    @ApiResponses(value = @ApiResponse(responseCode = "200"))
    public double getRouteByAddresses(@RequestParam("from") String from,
                                      @RequestParam("to") String to);

}
