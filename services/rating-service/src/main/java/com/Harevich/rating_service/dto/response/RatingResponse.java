package com.Harevich.rating_service.dto.response;

import com.Harevich.rating_service.model.enumerations.VotingPerson;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.bson.types.ObjectId;

import java.util.UUID;

public record RatingResponse(
        ObjectId rideId,
        UUID votingId,
        UUID votableId,
        byte rating,
        String feedback
) {
}
