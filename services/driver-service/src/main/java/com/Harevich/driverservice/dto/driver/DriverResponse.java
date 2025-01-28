package com.Harevich.driverservice.dto.driver;

import java.util.UUID;

public record DriverResponse(
        UUID id,
        String name,
        String surname,
        String email,
        UUID car_id
) {
}
