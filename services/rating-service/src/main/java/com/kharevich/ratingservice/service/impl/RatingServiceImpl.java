package com.kharevich.ratingservice.service.impl;

import com.kharevich.ratingservice.client.RideServiceClient;
import com.kharevich.ratingservice.dto.request.RatingRequest;
import com.kharevich.ratingservice.dto.response.PageableResponse;
import com.kharevich.ratingservice.dto.response.PersonalRatingResponse;
import com.kharevich.ratingservice.dto.response.RatingResponse;
import com.kharevich.ratingservice.exception.CantEstimateUnCompletedRideException;
import com.kharevich.ratingservice.model.Rating;
import com.kharevich.ratingservice.model.enumerations.RatingPerson;
import com.kharevich.ratingservice.repository.RatingRepository;
import com.kharevich.ratingservice.service.RatingService;
import com.kharevich.ratingservice.sideservices.ride.RideResponse;
import com.kharevich.ratingservice.sideservices.ride.enumerations.RideStatus;
import com.kharevich.ratingservice.util.mapper.RatingMapper;
import com.kharevich.ratingservice.util.validation.PersonValidationService;
import com.kharevich.ratingservice.util.validation.RatingValidationService;
import com.kharevich.ratingservice.util.validation.factory.PersonValidationServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.kharevich.ratingservice.model.enumerations.RatingPerson.DRIVER;
import static com.kharevich.ratingservice.model.enumerations.RatingPerson.PASSENGER;
import static com.kharevich.ratingservice.util.constants.RideServiceConstantResponses.CANT_ESTIMATE_UN_COMPLETED_RIDE;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RideServiceClient rideClient;

    private final RatingRepository ratingRepository;

    private final PersonValidationServiceFactory personValidationFactory;

    private final RatingValidationService ratingValidationService;

    private final RatingMapper ratingMapper;

    @Override
    public RatingResponse estimateTheRide(RatingRequest request) {
       
        PersonValidationService passengerValidation = personValidationFactory.validatorFor(PASSENGER);
        PersonValidationService driverValidation = personValidationFactory.validatorFor(DRIVER);

        RideResponse rideResponse = rideClient.getRideIfExists(request.rideId());

        if(!rideResponse.rideStatus().equals(RideStatus.FINISHED))
            throw new CantEstimateUnCompletedRideException(CANT_ESTIMATE_UN_COMPLETED_RIDE);

        passengerValidation.checkIfPersonExists(rideResponse.passengerId());
        driverValidation.checkIfPersonExists(rideResponse.driverId());

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
