package com.Harevich.driverservice.dto.car;

import java.util.UUID;

public record CarResponse(
        UUID id,
        String color,
        String number,
        String brand,
        UUID driver_id
) {
}
