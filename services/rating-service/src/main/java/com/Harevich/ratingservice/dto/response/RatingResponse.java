package com.Harevich.ratingservice.dto.response;

import java.util.UUID;

public record RatingResponse (

        UUID rideId,

        UUID ratedById,

        UUID ratedId,

        String whoIsRated,

        int rating,

        String feedback

) {
}
