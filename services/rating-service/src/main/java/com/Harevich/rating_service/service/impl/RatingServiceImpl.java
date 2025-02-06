package com.Harevich.rating_service.service.impl;

import com.Harevich.rating_service.dto.request.RatingRequest;
import com.Harevich.rating_service.dto.response.PageableResponse;
import com.Harevich.rating_service.dto.response.PersonalRatingResponse;
import com.Harevich.rating_service.dto.response.RatingResponse;
import com.Harevich.rating_service.model.Rating;
import com.Harevich.rating_service.model.enumerations.VotingPerson;
import com.Harevich.rating_service.repository.RatingRepository;
import com.Harevich.rating_service.service.RatingService;
import com.Harevich.rating_service.util.mapper.RatingMapper;
import com.Harevich.rating_service.util.validation.PersonValidationService;
import com.Harevich.rating_service.util.validation.RatingValidationService;
import com.Harevich.rating_service.util.validation.factory.PersonValidationServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    private final PersonValidationServiceFactory personValidationFactory;

    private final RatingValidationService ratingValidationService;

    private final RatingMapper ratingMapper;

    @Override
    public RatingResponse estimateTheRide(RatingRequest request) {
        Rating rating = ratingMapper.toRating(request);
        ratingRepository.save(rating);
        return ratingMapper.toResponse(rating);
    }

    @Override
    public PageableResponse<RatingResponse> getAllRatingsByPersonId(UUID id, VotingPerson whoVotes, int pageNumber, int size) {
        PersonValidationService validationService = personValidationFactory.getService(whoVotes);
        validationService.checkIfPersonExists(id);
        PageableResponse<RatingResponse> ratings = ratingValidationService
                .findAllRaitingsByPersonId(id, whoVotes, PageRequest.of(pageNumber,size));
        return ratings;
    }

    @Override
    public PersonalRatingResponse getPersonTotalRating(UUID id, VotingPerson whoVotes) {
        PersonValidationService validationService = personValidationFactory.getService(whoVotes);
        validationService.checkIfPersonExists(id);
        PageableResponse<RatingResponse> pageableResponse = ratingValidationService.findLastRaitingsByPersonId(id,whoVotes);
        Double average = pageableResponse.content().stream()
                .map(RatingResponse::rating)
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(5.0);
        return new PersonalRatingResponse(id,average);
    }

}
