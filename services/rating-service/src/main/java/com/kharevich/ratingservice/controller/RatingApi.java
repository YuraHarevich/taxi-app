package com.kharevich.ratingservice.controller;

import com.kharevich.ratingservice.dto.ErrorMessage;
import com.kharevich.ratingservice.dto.request.RatingRequest;
import com.kharevich.ratingservice.dto.response.PageableResponse;
import com.kharevich.ratingservice.dto.response.PersonalRatingResponse;
import com.kharevich.ratingservice.dto.response.RatingResponse;
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

@Tag(name = "RatingApi api",
        description = "This controller is made to rate rides")
public interface RatingApi {

    @Operation(summary = "estimating the ride")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ride successfully estimated"),
            @ApiResponse(responseCode = "400", description = "Invalid data format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "This ride was already rated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public RatingResponse estimateTheRide(@Valid @RequestBody RatingRequest request);

    @Operation(summary = "getting all available ratings by driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ratings successfully found"),
    })
    public PageableResponse<RatingResponse> getAllRatingsByDriverId(@Valid @RequestParam UUID driverId,
                                                                    @RequestParam(defaultValue = "0")@Min(0) int page_number,
                                                                    @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "getting all available ratings by passenger")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ratings successfully found"),
    })
    public PageableResponse<RatingResponse> getAllRatingsByPassengerId(@Valid @RequestParam UUID passengerId,
                                                                       @RequestParam(defaultValue = "0")@Min(0) int page_number,
                                                                       @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "getting passenger's rating by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating successfully found"),
            @ApiResponse(responseCode = "404", description = "Passenger not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public PersonalRatingResponse getPassengerRating(@Valid @RequestParam UUID passengerId);

    @Operation(summary = "getting driver's rating by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating successfully found"),
            @ApiResponse(responseCode = "404", description = "Driver not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    public PersonalRatingResponse getDriverRating(@Valid @RequestParam UUID driverId);

}
