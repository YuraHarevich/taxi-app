package com.Harevich.ratingservice.dto.request;

import com.Harevich.ratingservice.model.enumerations.RatingPerson;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.UUID;

public record RatingRequest(

        @Schema(description = "Ride id")
        @org.hibernate.validator.constraints.UUID
        UUID rideId,

        @Schema(description = "rated by id")
        UUID ratedById,

        @Schema(description = "rated id")
        UUID ratedId,

        @Max(5)
        @Min(1)
        @Schema(description = "rating(stars)", example = "5")
        int rating,

        @Schema(description = "who rates",example = "PASSENGER")
        RatingPerson whoIsRated,

        @Schema(description = "feedback about the ride", example = "в целом норм")
        String feedback

) {
}
