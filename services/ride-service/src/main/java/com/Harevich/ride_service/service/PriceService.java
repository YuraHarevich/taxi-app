package com.Harevich.ride_service.service;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public interface PriceService {
    BigDecimal getPriceByTwoAddresses(@NotBlank(message = "finish point is mandatory") String s, @NotBlank(message = "start point is mandatory") String from);
}
