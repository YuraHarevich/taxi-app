package com.Harevich.passenger_service.controller;

import com.Harevich.passenger_service.dto.ErrorMessage;
import com.Harevich.passenger_service.dto.PassengerRequest;
import com.Harevich.passenger_service.dto.PassengerResponse;
import com.Harevich.passenger_service.util.mapper.PassengerMapper;
import com.Harevich.passenger_service.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/passenger")
@Tag(name = "Passenger api",
        description = "This controller is made to communicate with passenger service")
public class PassengerController {
    private final PassengerService passengerService;
    @PostMapping("registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "registration of the passenger")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Passenger successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "This number/email is already in use",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public PassengerResponse registration(@Valid @RequestBody PassengerRequest request){
        return PassengerMapper.toResponse(passengerService.registrate(request));
    }


    @PatchMapping("edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
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
    public PassengerResponse edit(@RequestParam("id") UUID id,@Valid @RequestBody PassengerRequest request){
        return PassengerMapper.toResponse(passengerService.edit(request,id));
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "getting the passenger by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger successfully found"),
            @ApiResponse(responseCode = "404", description = "Passenger not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public PassengerResponse getPassengerById(@RequestParam("id") UUID id){
        return passengerService.getById(id);
    }


    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "getting the passenger by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Passenger not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public void deletePassengerById(@RequestParam("id") UUID id){
        passengerService.deleteById(id);
    }
}
