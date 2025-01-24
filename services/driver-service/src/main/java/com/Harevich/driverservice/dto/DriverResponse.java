package com.Harevich.driverservice.dto;

import java.util.Objects;

public record DriverResponse(
        Long id,
        String name,
        String surname,
        String email,
        Float rate,
        CarResponse car
) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DriverResponse that = (DriverResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
