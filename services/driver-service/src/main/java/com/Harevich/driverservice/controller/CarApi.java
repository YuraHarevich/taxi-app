package com.Harevich.driverservice.controller;

import com.Harevich.driverservice.dto.ErrorMessage;
import com.Harevich.driverservice.dto.request.CarRequest;
import com.Harevich.driverservice.dto.response.CarResponse;
import com.Harevich.driverservice.dto.response.PageableResponse;
import com.Harevich.driverservice.util.constants.RegularExpressionConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Car api",
        description = "This controller is made to communicate with cars from driver service")
public interface CarApi {
    @Operation(summary = "creating car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Car successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "This car number is already in use",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public CarResponse createNewCar(@Valid @RequestBody CarRequest request);

    @Operation(summary = "changing the car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Car successfully edited"),
            @ApiResponse(responseCode = "400", description = "Invalid data format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "This car number is already in use",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public CarResponse updateCar(@RequestParam("id") UUID id,
                                 @Valid @RequestBody CarRequest request);

    @Operation(summary = "getting the car by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car successfully found"),
            @ApiResponse(responseCode = "404", description = "Car not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public CarResponse getCarById(@RequestParam("id") UUID id);

    @Operation(summary = "getting the car by number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car successfully found"),
            @ApiResponse(responseCode = "400", description = "Invalid data format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Car not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public CarResponse getCarByNumber(@RequestParam("number") @Pattern(regexp = RegularExpressionConstants.CAR_NUMBER_REGEX) String number);

    @Operation(summary = "getting car by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Car not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public void deleteCarById(@RequestParam("id") UUID id);

    @Operation(summary = "getting all available cars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cars successfully found"),
    })
    public PageableResponse<CarResponse> getAllAvailableCars(@RequestParam(defaultValue = "0") @Min(0) int page_number,
                                                             @RequestParam(defaultValue = "10") int size);
}
