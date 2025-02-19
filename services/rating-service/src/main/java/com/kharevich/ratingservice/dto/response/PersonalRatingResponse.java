package com.kharevich.ratingservice.dto.response;

import java.util.UUID;

public record PersonalRatingResponse (

        UUID personId,

        double totalRating

) {
}
