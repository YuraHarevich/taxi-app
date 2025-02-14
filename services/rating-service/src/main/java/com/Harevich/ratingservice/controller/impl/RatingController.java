package com.Harevich.ratingservice.controller.impl;

import com.Harevich.ratingservice.controller.RatingApi;
import com.Harevich.ratingservice.dto.request.RatingRequest;
import com.Harevich.ratingservice.dto.response.PageableResponse;
import com.Harevich.ratingservice.dto.response.PersonalRatingResponse;
import com.Harevich.ratingservice.dto.response.RatingResponse;
import com.Harevich.ratingservice.model.enumerations.RatingPerson;
import com.Harevich.ratingservice.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/ratings")
@Validated
public class RatingController implements RatingApi {

    private final RatingService ratingService;

    @PostMapping("estimation")
    @ResponseStatus(HttpStatus.CREATED)
    public RatingResponse estimateTheRide(@Valid @RequestBody RatingRequest request) {
        return ratingService.estimateTheRide(request);
    }

    @GetMapping("driver/all")
    @ResponseStatus(HttpStatus.OK)
    public PageableResponse<RatingResponse> getAllRatingsByDriverId(@Valid @RequestParam UUID driverId,
                                                                    @RequestParam(defaultValue = "0") int pageNumber,
                                                                    @RequestParam(defaultValue = "10") int size) {
        PageableResponse<RatingResponse> response = ratingService.getAllRatingsByPersonId(
                driverId,
                RatingPerson.DRIVER,
                pageNumber,
                size > 50 ? 50 : size);
        return response;
    }

    @GetMapping("passenger/all")
    @ResponseStatus(HttpStatus.OK)
    public PageableResponse<RatingResponse> getAllRatingsByPassengerId(@Valid @RequestParam UUID passengerId,
                                                                       @RequestParam(defaultValue = "0") int pageNumber,
                                                                       @RequestParam(defaultValue = "10") int size) {
        PageableResponse<RatingResponse> response = ratingService.getAllRatingsByPersonId(
                passengerId,
                RatingPerson.PASSENGER,
                pageNumber,
                size > 50 ? 50 : size);
        return response;
    }

    @GetMapping("passenger")
    @ResponseStatus(HttpStatus.OK)
    public PersonalRatingResponse getPassengerRating(@Valid @RequestParam UUID passengerId) {
        PersonalRatingResponse response = ratingService.getPersonTotalRating(passengerId, RatingPerson.PASSENGER);
        return response;
    }

    @GetMapping("driver")
    @ResponseStatus(HttpStatus.OK)
    public PersonalRatingResponse getDriverRating(@Valid @RequestParam UUID driverId) {
        PersonalRatingResponse response = ratingService.getPersonTotalRating(driverId, RatingPerson.DRIVER);
        return response;
    }

}
