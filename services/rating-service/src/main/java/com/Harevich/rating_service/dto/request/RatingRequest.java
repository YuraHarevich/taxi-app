package com.Harevich.rating_service.dto.request;

import com.Harevich.rating_service.model.enumerations.VotingPerson;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.bson.types.ObjectId;

import java.util.UUID;

public record RatingRequest(
        @NotBlank(message = "Ride id is mandatory")
        @Schema(description = "Ride id", example = "656f1e5b8c8a4e2f98d0a2b1")
        ObjectId id,

        @NotBlank(message = "voting id is mandatory")
        UUID votingId,

        @NotBlank(message = "votable id is mandatory")
        UUID votableId,

        @NotBlank(message = "appraisal id is mandatory")
        @Max(5)
        @Min(1)
        byte rating,

        @NotBlank(message = "it is mandatory to chose voting person")
        VotingPerson whoVotes,

        @Schema(description = "feedback about the ride", example = "656f1e5b8c8a4e2f98d0a2b1")
        String feedback
) {
}
