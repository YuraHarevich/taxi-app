package com.Harevich.driverservice.dto.driver;

public record DriverRequest(
        String name,
        String surname,
        String email,
        String number
) {
}
