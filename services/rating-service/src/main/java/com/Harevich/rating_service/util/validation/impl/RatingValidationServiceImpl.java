package com.Harevich.rating_service.util.validation.impl;

import com.Harevich.rating_service.dto.response.PageableResponse;
import com.Harevich.rating_service.dto.response.RatingResponse;
import com.Harevich.rating_service.model.enumerations.VotingPerson;
import com.Harevich.rating_service.repository.RatingRepository;
import com.Harevich.rating_service.util.config.RatingParametersConsumer;
import com.Harevich.rating_service.util.mapper.PageMapper;
import com.Harevich.rating_service.util.mapper.RatingMapper;
import com.Harevich.rating_service.util.validation.RatingValidationService;
import lombok.RequiredArgsConstructor;
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
    public PageableResponse findAllRaitingsByPersonId(UUID id, VotingPerson whoVotes, Pageable pageable) {
        switch (whoVotes){
            case PASSENGER:
                return pageMapper.toResponse(ratingRepository.findByPassengerId(id, pageable));
            case DRIVER:
                return pageMapper.toResponse(ratingRepository.findByDriverId(id, pageable));
            default:
                throw new RuntimeException();//todo: нормальную ошибку выкинуть
        }
    }

    @Override
    public PageableResponse findLastRaitingsByPersonId(UUID id, VotingPerson whoVotes) {
        switch (whoVotes){
            case PASSENGER:
                ratingRepository.findByPassengerIdOrderByCreatedAtDesc(id, PageRequest.of(0,ratingParametersConsumer.getNumberOfRidesToEvaluateRating()));
            case DRIVER:
                ratingRepository.findByDriverIdOrderByCreatedAtDesc(id, PageRequest.of(0,ratingParametersConsumer.getNumberOfRidesToEvaluateRating()));
            default:
                throw new RuntimeException();//todo: нормальную ошибку выкинуть
        }
    }
}
