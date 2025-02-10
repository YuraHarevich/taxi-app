package com.Harevich.rating_service.util.mapper;

import com.Harevich.rating_service.dto.request.RatingRequest;
import com.Harevich.rating_service.dto.response.RatingResponse;
import com.Harevich.rating_service.model.Rating;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T22:35:33+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class RatingMapperImpl implements RatingMapper {

    @Override
    public Rating toRating(RatingRequest request) {
        if ( request == null ) {
            return null;
        }

        Rating rating = new Rating();

        rating.setRideId( request.rideId() );
        rating.setVotingId( request.votingId() );
        rating.setVotableId( request.votableId() );
        rating.setRating( request.rating() );
        rating.setWhoVotes( request.whoVotes() );
        rating.setFeedback( request.feedback() );

        rating.setRatingTime( java.time.LocalDateTime.now() );

        return rating;
    }

    @Override
    public RatingResponse toResponse(Rating rating) {
        if ( rating == null ) {
            return null;
        }

        String whoVotes = null;
        UUID rideId = null;
        UUID votingId = null;
        UUID votableId = null;
        int rating1 = 0;
        String feedback = null;

        whoVotes = whoVotesToString( rating.getWhoVotes() );
        rideId = rating.getRideId();
        votingId = rating.getVotingId();
        votableId = rating.getVotableId();
        rating1 = rating.getRating();
        feedback = rating.getFeedback();

        RatingResponse ratingResponse = new RatingResponse( rideId, votingId, votableId, whoVotes, rating1, feedback );

        return ratingResponse;
    }
}
