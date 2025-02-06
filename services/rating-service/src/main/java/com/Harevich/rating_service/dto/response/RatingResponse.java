package com.Harevich.rating_service.dto.response;

import java.util.UUID;

public record RatingResponse(
        UUID rideId,
        UUID votingId,
        UUID votableId,
        byte rating,
        String feedback
) {
}
