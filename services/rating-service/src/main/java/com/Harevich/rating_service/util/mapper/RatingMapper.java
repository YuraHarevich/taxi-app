package com.Harevich.rating_service.util.mapper;

import com.Harevich.rating_service.dto.request.RatingRequest;
import com.Harevich.rating_service.dto.response.RatingResponse;
import com.Harevich.rating_service.model.Rating;
import com.Harevich.rating_service.model.enumerations.VotingPerson;
import org.bson.types.ObjectId;
import org.mapstruct.*;

import java.util.Locale;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RatingMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "ratingTime", expression = "java(java.time.LocalDateTime.now())")
    Rating toRating(RatingRequest request);

    @Mapping(source = "whoVotes", target = "whoVotes", qualifiedByName = "whoVotesToString")
    @Mapping(source = "rideId", target = "rideId")
    RatingResponse toResponse(Rating rating);

    @Named("whoVotesToString")
    default String whoVotesToString(VotingPerson whoVotes) {
        return whoVotes != null ? whoVotes.name().toLowerCase(Locale.ROOT) : null;
    }

}
