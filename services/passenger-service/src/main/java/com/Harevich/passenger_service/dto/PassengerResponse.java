package com.Harevich.passenger_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;

import java.util.Objects;
import java.util.UUID;

public record PassengerResponse(
        UUID id,
        String name,
        String surname,
        String email,
        String number
) {
}
