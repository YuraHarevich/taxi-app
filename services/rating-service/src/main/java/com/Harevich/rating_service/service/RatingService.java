package com.Harevich.rating_service.service;

import com.Harevich.rating_service.dto.request.RatingRequest;
import com.Harevich.rating_service.dto.response.PageableResponse;
import com.Harevich.rating_service.dto.response.PersonalRatingResponse;
import com.Harevich.rating_service.dto.response.RatingResponse;
import com.Harevich.rating_service.model.enumerations.VotingPerson;
import jakarta.validation.Valid;

import java.util.UUID;

public interface RatingService {

    RatingResponse estimateTheRide(@Valid RatingRequest request);

    PageableResponse<RatingResponse> getAllRatingsByPersonId(UUID Id, VotingPerson whoVotes, int pageNumber, int size);

    PersonalRatingResponse getPersonTotalRating(UUID passengerId, VotingPerson whoVotes);

}
