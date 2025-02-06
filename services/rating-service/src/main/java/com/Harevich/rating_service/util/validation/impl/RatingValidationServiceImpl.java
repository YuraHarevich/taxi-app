package com.Harevich.rating_service.util.validation.impl;

import com.Harevich.rating_service.dto.response.PageableResponse;
import com.Harevich.rating_service.dto.response.RatingResponse;
import com.Harevich.rating_service.model.Rating;
import com.Harevich.rating_service.model.enumerations.VotingPerson;
import com.Harevich.rating_service.repository.RatingRepository;
import com.Harevich.rating_service.util.config.RatingParametersConsumer;
import com.Harevich.rating_service.util.mapper.PageMapper;
import com.Harevich.rating_service.util.mapper.RatingMapper;
import com.Harevich.rating_service.util.validation.RatingValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingValidationServiceImpl implements RatingValidationService {

    private final RatingRepository ratingRepository;

    private final PageMapper pageMapper;

    private final RatingMapper ratingMapper;

    private final RatingParametersConsumer ratingParametersConsumer;

    @Override
    public PageableResponse<RatingResponse>  findAllRaitingsByPersonId(UUID id, VotingPerson whoVotes, Pageable pageable) {
        Page<RatingResponse> page = ratingRepository
                .findByVotableIdAndWhoVotes(id,whoVotes,pageable)
                .map(ratingMapper::toResponse);
        return pageMapper.toResponse(page);
    }

    @Override
    public PageableResponse<RatingResponse>  findLastRaitingsByPersonId(UUID id, VotingPerson whoVotes) {
        Page<RatingResponse> page = ratingRepository
                .findByVotableIdAndWhoVotesOrderByRatingTimeDesc(
                        id,
                        whoVotes,
                        PageRequest.of(0,ratingParametersConsumer.getNumberOfRidesToEvaluateRating()))
                .map(ratingMapper::toResponse);
        return pageMapper.toResponse(page);
    }

}
