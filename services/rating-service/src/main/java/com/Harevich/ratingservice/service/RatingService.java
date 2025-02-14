package com.Harevich.ratingservice.service;

import com.Harevich.ratingservice.dto.request.RatingRequest;
import com.Harevich.ratingservice.dto.response.PageableResponse;
import com.Harevich.ratingservice.dto.response.PersonalRatingResponse;
import com.Harevich.ratingservice.dto.response.RatingResponse;
import com.Harevich.ratingservice.model.enumerations.RatingPerson;
import jakarta.validation.Valid;

import java.util.UUID;

public interface RatingService {

    RatingResponse estimateTheRide(@Valid RatingRequest request);

    PageableResponse<RatingResponse> getAllRatingsByPersonId(UUID Id, RatingPerson whoIsRated, int pageNumber, int size);

    PersonalRatingResponse getPersonTotalRating(UUID passengerId, RatingPerson whoIsRated);

}
