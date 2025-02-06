package com.Harevich.rating_service.util.validation;

import com.Harevich.rating_service.dto.response.PageableResponse;
import com.Harevich.rating_service.dto.response.RatingResponse;
import com.Harevich.rating_service.model.enumerations.VotingPerson;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RatingValidationService {

    PageableResponse<RatingResponse> findAllRaitingsByPersonId(UUID id, VotingPerson whoVotes, Pageable pageable);

    PageableResponse<RatingResponse> findLastRaitingsByPersonId(UUID id, VotingPerson whoVotes);

}
