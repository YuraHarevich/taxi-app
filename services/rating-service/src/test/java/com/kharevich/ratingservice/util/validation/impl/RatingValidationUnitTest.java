package com.kharevich.ratingservice.util.validation.impl;


import com.kharevich.ratingservice.dto.response.PageableResponse;
import com.kharevich.ratingservice.dto.response.RatingResponse;
import com.kharevich.ratingservice.exception.RideAlreadyEstimatedException;
import com.kharevich.ratingservice.model.Rating;
import com.kharevich.ratingservice.model.enumerations.RatingPerson;
import com.kharevich.ratingservice.repository.RatingRepository;
import com.kharevich.ratingservice.util.config.RatingParametersConsumer;
import com.kharevich.ratingservice.util.mapper.PageMapper;
import com.kharevich.ratingservice.util.mapper.RatingMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.kharevich.ratingservice.model.enumerations.RatingPerson.DRIVER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingValidationUnitTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private PageMapper pageMapper;

    @Mock
    private RatingMapper ratingMapper;

    @Mock
    private RatingParametersConsumer ratingParametersConsumer;

    @InjectMocks
    private RatingValidationServiceImpl ratingValidationService;

    private UUID userId;
    private UUID rideId;
    private RatingPerson ratingPerson;
    private Pageable pageable;
    private Page<Rating> ratingPage;
    private RatingResponse ratingResponse;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        rideId = UUID.randomUUID();
        ratingPerson = DRIVER;
        pageable = PageRequest.of(0, 10);
        ratingResponse = new RatingResponse(UUID.randomUUID(),
                DRIVER.toString(),
                4,
                "so so");
        ratingPage = new PageImpl<>(Collections.singletonList(new Rating()));
    }

    @Test
    void testFindAllRatingsByPersonId() {
        when(ratingRepository.findByRatedIdAndWhoIsRated(userId, ratingPerson, pageable))
                .thenReturn(ratingPage);
        when(ratingMapper.toResponse(any())).thenReturn(ratingResponse);
        when(pageMapper.toResponse(any())).thenReturn(new PageableResponse<>(1,1,1,1, List.of(ratingResponse)));

        PageableResponse<RatingResponse> response = ratingValidationService.findAllRatingsByPersonId(userId, ratingPerson, pageable);

        assertNotNull(response);
        verify(ratingRepository).findByRatedIdAndWhoIsRated(userId, ratingPerson, pageable);
        verify(pageMapper).toResponse(any());
    }

    @Test
    void testFindLastRatingsByPersonId() {
        when(ratingParametersConsumer.getNumberOfRidesToEvaluateRating()).thenReturn(5);
        when(ratingRepository.findByRatedIdAndWhoIsRatedOrderByRatingTimeDesc(userId, ratingPerson, PageRequest.of(0, 5)))
                .thenReturn(ratingPage);
        when(ratingMapper.toResponse(any())).thenReturn(ratingResponse);
        when(pageMapper.toResponse(any())).thenReturn(new PageableResponse<>(1,1,1,1, List.of(ratingResponse)));

        PageableResponse<RatingResponse> response = ratingValidationService.findLastRatingsByPersonId(userId, ratingPerson);

        assertNotNull(response);
        verify(ratingRepository).findByRatedIdAndWhoIsRatedOrderByRatingTimeDesc(userId, ratingPerson, PageRequest.of(0, 5));
        verify(pageMapper).toResponse(any());
    }

    @Test
    void testCheckIfRideIsAlreadyEstimated_ThrowsException() {
        when(ratingRepository.findByRideIdAndWhoIsRated(rideId, ratingPerson)).thenReturn(Optional.of(new Rating()));

        assertThrows(RideAlreadyEstimatedException.class,
                () -> ratingValidationService.checkIfRideIsAlreadyEstimated(rideId, ratingPerson));

        verify(ratingRepository).findByRideIdAndWhoIsRated(rideId, ratingPerson);
    }

    @Test
    void testCheckIfRideIsAlreadyEstimated_NoException() {
        when(ratingRepository.findByRideIdAndWhoIsRated(rideId, ratingPerson)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> ratingValidationService.checkIfRideIsAlreadyEstimated(rideId, ratingPerson));

        verify(ratingRepository).findByRideIdAndWhoIsRated(rideId, ratingPerson);
    }
}



