package com.kharevich.ratingservice.util.validation.impl;

import com.kharevich.ratingservice.dto.response.PageableResponse;
import com.kharevich.ratingservice.dto.response.RatingResponse;
import com.kharevich.ratingservice.exception.RideAlreadyEstimatedException;
import com.kharevich.ratingservice.model.enumerations.RatingPerson;
import com.kharevich.ratingservice.repository.RatingRepository;
import com.kharevich.ratingservice.util.config.RatingParametersConsumer;
import com.kharevich.ratingservice.util.constants.RideServiceConstantResponses;
import com.kharevich.ratingservice.util.mapper.PageMapper;
import com.kharevich.ratingservice.util.mapper.RatingMapper;
import com.kharevich.ratingservice.util.validation.RatingValidationService;
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
    public PageableResponse<RatingResponse> findAllRatingsByPersonId(UUID id, RatingPerson whoIsRated, Pageable pageable) {
        Page<RatingResponse> page = ratingRepository
                .findByRatedIdAndWhoIsRated(
                        id,
                        whoIsRated,
                        pageable)
                .map(ratingMapper::toResponse);
        return pageMapper.toResponse(page);
    }

    @Override
    public PageableResponse<RatingResponse>  findLastRatingsByPersonId(UUID id, RatingPerson whoIsRated) {
        Page<RatingResponse> page = ratingRepository
                .findByRatedIdAndWhoIsRatedOrderByRatingTimeDesc(
                        id,
                        whoIsRated,
                        PageRequest.of(0,ratingParametersConsumer.getNumberOfRidesToEvaluateRating()))
                .map(ratingMapper::toResponse);
        return pageMapper.toResponse(page);
    }

    @Override
    public void checkIfRideIsAlreadyEstimated(UUID rideId, RatingPerson whoIsRated) {
        if(ratingRepository.findByRideIdAndWhoIsRated(rideId,whoIsRated).isPresent())
                throw new RideAlreadyEstimatedException(
                        RideServiceConstantResponses.RIDE_IS_ALREADY_ESTIMATED.formatted(rideId,whoIsRated));
    }

}
