package com.Harevich.ratingservice.sideservices.driver;

import com.Harevich.ratingservice.sideservices.driver.enumerations.Sex;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record DriverResponse(
        UUID id,
        @Schema(description = "Drivers name", example = "Vlad")
        String name,
        @Schema(description = "Drivers surname", example = "Vvvvvlad")
        String surname,
        @Schema(description = "Drivers email", example = "Vlad@gmail.com")
        String email,
        @Schema(description = "Drivers sex", example = "male")
        Sex sex,
        @Schema(description = "Drivers car")
        UUID carId
) {
}
