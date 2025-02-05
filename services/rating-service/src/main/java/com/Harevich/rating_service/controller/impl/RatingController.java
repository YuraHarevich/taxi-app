package com.Harevich.rating_service.controller.impl;

import com.Harevich.rating_service.controller.RatingApi;
import com.Harevich.rating_service.dto.request.RatingRequest;
import com.Harevich.rating_service.dto.response.PageableResponse;
import com.Harevich.rating_service.dto.response.PersonalRatingResponse;
import com.Harevich.rating_service.dto.response.RatingResponse;
import com.Harevich.rating_service.model.enumerations.VotingPerson;
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
    public PageableResponse getAllRatingsByDriverId(@RequestParam UUID driverId,
                                                    @RequestParam int page_number,
                                                    @RequestParam int size) {
        PageableResponse response = ratingService.getAllRatingsByPersonId(
                driverId,
                VotingPerson.DRIVER,
                page_number,
                size);
        return response;
    }

    @GetMapping("passenger/all")
    @ResponseStatus(HttpStatus.OK)
    public PageableResponse getAllRatingsByPassengerId(@RequestParam UUID passengerId,
                                                       @RequestParam int page_number,
                                                       @RequestParam int size) {
        PageableResponse response = ratingService.getAllRatingsByPersonId(
                passengerId,
                VotingPerson.PASSENGER,
                page_number,
                size);
        return response;
    }

    @GetMapping("passenger")
    @ResponseStatus(HttpStatus.OK)
    public PersonalRatingResponse getPassengerRating(@RequestParam UUID passengerId) {
        PersonalRatingResponse response = ratingService.getPersonTotalRating(passengerId, VotingPerson.PASSENGER);
        return response;
    }

    @GetMapping("driver")
    @ResponseStatus(HttpStatus.OK)
    public PersonalRatingResponse getDriverRating(@RequestParam UUID driverId) {
        PersonalRatingResponse response = ratingService.getPersonTotalRating(driverId, VotingPerson.DRIVER);
        return response;
    }
}
