package com.Harevich.passengerservice.controller;
import com.Harevich.passengerservice.dto.ErrorMessage;
import com.Harevich.passengerservice.dto.PassengerRequest;
import com.Harevich.passengerservice.dto.PassengerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Tag(name = "Passenger api",
        description = "This controller is made to communicate with passenger service")
public interface PassengerApi {

    @Operation(summary = "creating passenger")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Passenger successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "This number/email is already in use",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public PassengerResponse createPassenger(@Valid @RequestBody PassengerRequest request);

    @Operation(summary = "changing the passenger")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Passenger successfully edited"),
            @ApiResponse(responseCode = "400", description = "Invalid data format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "This number/email is already in use",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public PassengerResponse updatePassenger(@RequestParam("id") UUID id, @Valid @RequestBody PassengerRequest request);

    @Operation(summary = "getting the passenger by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger successfully found"),
            @ApiResponse(responseCode = "404", description = "Passenger not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public PassengerResponse getPassengerById(@RequestParam("id") UUID id);

    @Operation(summary = "deleting the passenger by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Passenger not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public void deletePassengerById(@RequestParam("id") UUID id);
}