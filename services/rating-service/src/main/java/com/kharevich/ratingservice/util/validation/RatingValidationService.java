package com.kharevich.ratingservice.util.validation;

import com.kharevich.ratingservice.dto.response.PageableResponse;
import com.kharevich.ratingservice.dto.response.RatingResponse;
import com.kharevich.ratingservice.model.enumerations.RatingPerson;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RatingValidationService {

    PageableResponse<RatingResponse> findAllRatingsByPersonId(UUID id, RatingPerson whoIsRated, Pageable pageable);

    PageableResponse<RatingResponse> findLastRatingsByPersonId(UUID id, RatingPerson whoIsRated);

    void checkIfRideIsAlreadyEstimated(UUID rideId, RatingPerson ratingPerson);

}
