package com.kharevich.ratingservice.dto.response;

import java.util.UUID;

public record RatingResponse (

        UUID rideId,

        String whoIsRated,

        int rating,

        String feedback

) {
}
