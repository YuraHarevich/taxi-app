package com.kharevich.ratingservice.service.impl;

import com.kharevich.ratingservice.dto.request.RatingRequest;
import com.kharevich.ratingservice.dto.response.PageableResponse;
import com.kharevich.ratingservice.dto.response.PersonalRatingResponse;
import com.kharevich.ratingservice.dto.response.RatingResponse;
import com.kharevich.ratingservice.model.Rating;
import com.kharevich.ratingservice.model.enumerations.RatingPerson;
import com.kharevich.ratingservice.repository.RatingRepository;
import com.kharevich.ratingservice.service.RatingService;
import com.kharevich.ratingservice.util.mapper.RatingMapper;
import com.kharevich.ratingservice.util.validation.PersonValidationService;
import com.kharevich.ratingservice.util.validation.RatingValidationService;
import com.kharevich.ratingservice.util.validation.factory.PersonValidationServiceFactory;
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
        //todo получать id пассажира и водителя из другого сервиса
        Rating rating = ratingMapper.toRating(request);

        ratingValidationService.checkIfRideIsAlreadyEstimated(request.rideId(),request.whoIsRated());

        ratingRepository.save(rating);
        return ratingMapper.toResponse(rating);
    }

    @Override
    public PageableResponse<RatingResponse> getAllRatingsByPersonId(UUID id, RatingPerson whoIsRated, int pageNumber, int size) {
        PersonValidationService validationService = personValidationFactory.validatorFor(whoIsRated);
        validationService.checkIfPersonExists(id);
        PageableResponse<RatingResponse> ratings = ratingValidationService
                .findAllRatingsByPersonId(id, whoIsRated, PageRequest.of(pageNumber,size));
        return ratings;
    }

    @Override
    public PersonalRatingResponse getPersonTotalRating(UUID id, RatingPerson whoIsRated) {
        PersonValidationService validationService = personValidationFactory.validatorFor(whoIsRated);
        validationService.checkIfPersonExists(id);
        PageableResponse<RatingResponse> pageableResponse = ratingValidationService.findLastRatingsByPersonId(id,whoIsRated);
        Double average = pageableResponse.content().stream()
                .map(RatingResponse::rating)
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(5.0);
        return new PersonalRatingResponse(id,average);
    }

}
