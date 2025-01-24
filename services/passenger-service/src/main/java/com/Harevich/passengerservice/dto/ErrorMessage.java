package com.Harevich.passengerservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorMessage {
    @Schema(description = "Error message", example = "Some error message")
    private String message;
    private LocalDateTime timestamp;
}
