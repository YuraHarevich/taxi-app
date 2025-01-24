package com.Harevich.driverservice.dto;

public record DriverRequest(
        String name,
        String surname,
        String email,
        CarRequest car
) {
}
