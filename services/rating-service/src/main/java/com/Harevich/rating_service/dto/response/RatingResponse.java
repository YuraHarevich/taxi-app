package com.Harevich.rating_service.dto.response;

import com.Harevich.rating_service.model.enumerations.VotingPerson;
import org.bson.types.ObjectId;

import java.util.UUID;

public record RatingResponse(

        UUID rideId,

        UUID votingId,

        UUID votableId,

        String whoVotes,

        int rating,

        String feedback

) {
}
