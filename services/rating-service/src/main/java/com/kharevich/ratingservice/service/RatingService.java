package com.kharevich.ratingservice.service;

import com.kharevich.ratingservice.dto.request.RatingRequest;
import com.kharevich.ratingservice.dto.response.PageableResponse;
import com.kharevich.ratingservice.dto.response.PersonalRatingResponse;
import com.kharevich.ratingservice.dto.response.RatingResponse;
import com.kharevich.ratingservice.model.enumerations.RatingPerson;
import jakarta.validation.Valid;

import java.util.UUID;

public interface RatingService {

    RatingResponse estimateTheRide(@Valid RatingRequest request);

    PageableResponse<RatingResponse> getAllRatingsByPersonId(UUID Id, RatingPerson whoIsRated, int pageNumber, int size);

    PersonalRatingResponse getPersonTotalRating(UUID passengerId, RatingPerson whoIsRated);

}
