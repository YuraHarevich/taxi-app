package com.Harevich.rating_service.util.mapper;

import com.Harevich.rating_service.dto.request.RatingRequest;
import com.Harevich.rating_service.dto.response.RatingResponse;
import com.Harevich.rating_service.model.Rating;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RatingMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "ratingTime", expression = "java(java.time.LocalDateTime.now())")
    Rating toRating(RatingRequest request);

    RatingResponse toResponse(Rating rating);
}
