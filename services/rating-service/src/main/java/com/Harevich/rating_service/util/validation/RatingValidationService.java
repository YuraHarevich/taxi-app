package com.Harevich.rating_service.util.validation;

import com.Harevich.rating_service.dto.response.PageableResponse;
import com.Harevich.rating_service.dto.response.RatingResponse;
import com.Harevich.rating_service.model.enumerations.RatingPerson;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RatingValidationService {

    PageableResponse<RatingResponse> findAllRatingsByPersonId(UUID id, RatingPerson whoIsRated, Pageable pageable);

    PageableResponse<RatingResponse> findLastRatingsByPersonId(UUID id, RatingPerson whoIsRated);

    void checkIfRideIsAlreadyEstimated(UUID rideId, RatingPerson ratingPerson);
}
