package com.Harevich.rating_service.util.mapper;

import com.Harevich.rating_service.dto.request.RatingRequest;
import com.Harevich.rating_service.dto.response.RatingResponse;
import com.Harevich.rating_service.model.Rating;
import com.Harevich.rating_service.model.enumerations.RatingPerson;
import org.mapstruct.*;

import java.util.Locale;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RatingMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "ratingTime", expression = "java(java.time.LocalDateTime.now())")
    Rating toRating(RatingRequest request);

    @Mapping(source = "whoIsRated", target = "whoIsRated", qualifiedByName = "whoIsRatedToString")
    @Mapping(source = "rideId", target = "rideId")
    RatingResponse toResponse(Rating rating);

    @Named("whoIsRatedToString")
    default String whoIsRatedToString(RatingPerson whoIsRated) {
        return whoIsRated != null ? whoIsRated.name().toLowerCase(Locale.ROOT) : null;
    }

}
