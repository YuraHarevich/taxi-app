package com.kharevich.ratingservice.service.impl;

import static com.kharevich.ratingservice.model.enumerations.RatingPerson.DRIVER;
import static com.kharevich.ratingservice.sideservices.ride.enumerations.RideStatus.ACCEPTED;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.kharevich.ratingservice.client.ride.RideServiceClient;
import com.kharevich.ratingservice.dto.request.RatingRequest;
import com.kharevich.ratingservice.dto.response.PageableResponse;
import com.kharevich.ratingservice.dto.response.PersonalRatingResponse;
import com.kharevich.ratingservice.dto.response.RatingResponse;
import com.kharevich.ratingservice.model.Rating;
import com.kharevich.ratingservice.model.enumerations.RatingPerson;
import com.kharevich.ratingservice.repository.RatingRepository;
import com.kharevich.ratingservice.sideservices.ride.RideResponse;
import com.kharevich.ratingservice.util.mapper.RatingMapper;
import com.kharevich.ratingservice.util.validation.PersonValidationService;
import com.kharevich.ratingservice.util.validation.RatingValidationService;
import com.kharevich.ratingservice.util.validation.factory.PersonValidationServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class RatingServiceUnitTest {

    @Mock
    private RideServiceClient rideClient;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private PersonValidationServiceFactory personValidationFactory;

    @Mock
    private RatingValidationService ratingValidationService;

    @Mock
    private RatingMapper ratingMapper;

    @InjectMocks
    private RatingServiceImpl ratingService;

    private RatingRequest ratingRequest;
    private RideResponse rideResponse;
    private Rating rating;
    private RatingResponse ratingResponse;
    private UUID rideId;
    private UUID passengerId;
    private UUID driverId;

    @BeforeEach
    public void setUp() {
        rideId = UUID.randomUUID();
        passengerId = UUID.randomUUID();
        driverId = UUID.randomUUID();

        ratingRequest = new RatingRequest(rideId,5, DRIVER,"so so");
        rideResponse = new RideResponse(rideId,"from","to", BigDecimal.ONE, passengerId, driverId,ACCEPTED, Duration.ZERO);
        rating = new Rating();
        ratingResponse = new RatingResponse(rideId, DRIVER.toString(), 5, "so so");
    }

    @Test
    public void testEstimateTheRide() {
        PersonValidationService passengerValidation = mock(PersonValidationService.class);
        PersonValidationService driverValidation = mock(PersonValidationService.class);

        when(personValidationFactory.validatorFor(RatingPerson.PASSENGER)).thenReturn(passengerValidation);
        when(personValidationFactory.validatorFor(DRIVER)).thenReturn(driverValidation);
        when(rideClient.getRideIfExists(rideId)).thenReturn(rideResponse);
        when(ratingMapper.toRating(ratingRequest)).thenReturn(rating);
        when(ratingRepository.save(rating)).thenReturn(rating);
        when(ratingMapper.toResponse(rating)).thenReturn(ratingResponse);

        RatingResponse result = ratingService.estimateTheRide(ratingRequest);

        assertNotNull(result);
        assertEquals(ratingResponse.rating(), result.rating());

        verify(passengerValidation).checkIfPersonExists(passengerId);
        verify(driverValidation).checkIfPersonExists(driverId);
        verify(ratingValidationService).checkIfRideIsAlreadyEstimated(rideId, DRIVER);
        verify(ratingRepository).save(rating);
    }

    @Test
    public void testGetAllRatingsByPersonId() {
        UUID personId = UUID.randomUUID();
        RatingPerson whoIsRated = DRIVER;
        int pageNumber = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        PersonValidationService validationService = mock(PersonValidationService.class);
        PageableResponse<RatingResponse> pageableResponse = new PageableResponse<>(1,1,1,1, List.of(ratingResponse));

        when(personValidationFactory.validatorFor(whoIsRated)).thenReturn(validationService);
        when(ratingValidationService.findAllRatingsByPersonId(personId, whoIsRated, pageRequest)).thenReturn(pageableResponse);

        PageableResponse<RatingResponse> result = ratingService.getAllRatingsByPersonId(personId, whoIsRated, pageNumber, size);

        assertNotNull(result);
        assertEquals(1, result.content().size());

        verify(validationService).checkIfPersonExists(personId);
        verify(ratingValidationService).findAllRatingsByPersonId(personId, whoIsRated, pageRequest);
    }

    @Test
    public void testGetPersonTotalRating() {
        UUID personId = UUID.randomUUID();
        RatingPerson whoIsRated = DRIVER;

        PersonValidationService validationService = mock(PersonValidationService.class);
        PageableResponse<RatingResponse> pageableResponse = new PageableResponse<>(1,1,1,1, Collections.emptyList());

        when(personValidationFactory.validatorFor(whoIsRated)).thenReturn(validationService);
        when(ratingValidationService.findLastRatingsByPersonId(personId, whoIsRated)).thenReturn(pageableResponse);

        PersonalRatingResponse result = ratingService.getPersonTotalRating(personId, whoIsRated);

        assertNotNull(result);
        assertEquals(5.0, result.totalRating());

        verify(validationService).checkIfPersonExists(personId);
        verify(ratingValidationService).findLastRatingsByPersonId(personId, whoIsRated);
    }
}