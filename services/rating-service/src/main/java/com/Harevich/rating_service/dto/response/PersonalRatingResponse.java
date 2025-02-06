package com.Harevich.rating_service.dto.response;

import java.util.UUID;

public record PersonalRatingResponse (

        UUID personId,

        double totalRating

) {
}
