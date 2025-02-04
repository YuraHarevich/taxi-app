package com.Harevich.ride_service.service;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PriceService {
    BigDecimal getPriceByTwoAddresses(@NotBlank(message = "finish point is mandatory") String from,
                                      @NotBlank(message = "start point is mandatory") String to,
                                      LocalDateTime currentTime);
}
