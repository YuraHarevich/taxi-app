package com.Harevich.ratingservice.service.impl;

import com.Harevich.ratingservice.client.RideServiceClient;
import com.Harevich.ratingservice.dto.request.RatingRequest;
import com.Harevich.ratingservice.dto.response.PageableResponse;
import com.Harevich.ratingservice.dto.response.PersonalRatingResponse;
import com.Harevich.ratingservice.dto.response.RatingResponse;
import com.Harevich.ratingservice.model.Rating;
import com.Harevich.ratingservice.model.enumerations.RatingPerson;
import com.Harevich.ratingservice.repository.RatingRepository;
import com.Harevich.ratingservice.service.RatingService;
import com.Harevich.ratingservice.sideservices.ride.RideResponse;
import com.Harevich.ratingservice.util.mapper.RatingMapper;
import com.Harevich.ratingservice.util.validation.PersonValidationService;
import com.Harevich.ratingservice.util.validation.RatingValidationService;
import com.Harevich.ratingservice.util.validation.factory.PersonValidationServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.Harevich.ratingservice.model.enumerations.RatingPerson.DRIVER;
import static com.Harevich.ratingservice.model.enumerations.RatingPerson.PASSENGER;

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
        //todo получать id пассажира и водителя из другого сервиса
        PersonValidationService passengerValidation = personValidationFactory.validatorFor(PASSENGER);
        PersonValidationService driverValidation = personValidationFactory.validatorFor(DRIVER);

        RideResponse rideResponse = rideClient.getRideIfExists(request.rideId());

        passengerValidation.checkIfPersonExists(rideResponse.passengerId());
        passengerValidation.checkIfPersonExists(rideResponse.driverId());

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
