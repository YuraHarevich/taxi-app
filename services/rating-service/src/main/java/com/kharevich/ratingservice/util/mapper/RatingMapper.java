package com.kharevich.ratingservice.util.mapper;

import com.kharevich.ratingservice.dto.request.RatingRequest;
import com.kharevich.ratingservice.dto.response.RatingResponse;
import com.kharevich.ratingservice.model.Rating;
import com.kharevich.ratingservice.model.enumerations.RatingPerson;
import org.mapstruct.*;

import java.util.Locale;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RatingMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "ratingTime", expression = "java(java.time.LocalDateTime.now())")
    Rating toRating(RatingRequest request, UUID ratedById, UUID ratedId);

    @Mapping(source = "whoIsRated", target = "whoIsRated", qualifiedByName = "whoIsRatedToString")
    @Mapping(source = "rideId", target = "rideId")
    RatingResponse toResponse(Rating rating);

    @Named("whoIsRatedToString")
    default String whoIsRatedToString(RatingPerson whoIsRated) {
        return whoIsRated != null ? whoIsRated.name().toLowerCase(Locale.ROOT) : null;
    }

}
