package com.Harevich.rating_service.controller.impl;

import com.Harevich.rating_service.controller.RatingApi;
import com.Harevich.rating_service.dto.request.RatingRequest;
import com.Harevich.rating_service.dto.response.PageableResponse;
import com.Harevich.rating_service.dto.response.PersonalRatingResponse;
import com.Harevich.rating_service.dto.response.RatingResponse;
import com.Harevich.rating_service.model.enumerations.RatingPerson;
import com.Harevich.rating_service.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/ratings")
public class RatingController implements RatingApi {

    private final RatingService ratingService;

    @PostMapping("estimate")
    @ResponseStatus(HttpStatus.CREATED)
    public RatingResponse estimateTheRide(@Valid @RequestBody RatingRequest request) {
        return ratingService.estimateTheRide(request);
    }

    @GetMapping("driver/all")
    @ResponseStatus(HttpStatus.OK)
    public PageableResponse<RatingResponse> getAllRatingsByDriverId(@Valid @RequestParam UUID driverId,
                                                                    @RequestParam(defaultValue = "0") int page_number,
                                                                    @RequestParam(defaultValue = "10") int size) {
        PageableResponse<RatingResponse> response = ratingService.getAllRatingsByPersonId(
                driverId,
                RatingPerson.DRIVER,
                page_number,
                size>50?50:size);
        return response;
    }

    @GetMapping("passenger/all")
    @ResponseStatus(HttpStatus.OK)
    public PageableResponse<RatingResponse> getAllRatingsByPassengerId(@Valid @RequestParam UUID passengerId,
                                                                       @RequestParam(defaultValue = "0") int page_number,
                                                                       @RequestParam(defaultValue = "10") int size) {
        PageableResponse<RatingResponse> response = ratingService.getAllRatingsByPersonId(
                passengerId,
                RatingPerson.PASSENGER,
                page_number,
                size>50?50:size);
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
