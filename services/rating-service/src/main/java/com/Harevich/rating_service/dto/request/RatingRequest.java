package com.Harevich.rating_service.dto.request;

import com.Harevich.rating_service.model.enumerations.VotingPerson;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record RatingRequest(

        @Schema(description = "Ride id")
        UUID rideId,

        @Schema(description = "voting id")
        UUID votingId,

        @Schema(description = "votable id")
        UUID votableId,

        @Max(5)
        @Min(1)
        @Schema(description = "rating(stars)", example = "5")
        byte rating,

        @Schema(description = "voting id",example = "PASSENGER")
        VotingPerson whoVotes,

        @Schema(description = "feedback about the ride", example = "Ну типо в целом норм но тот факт, что он не дал папарить это конечно кринж")
        String feedback

) {
}
