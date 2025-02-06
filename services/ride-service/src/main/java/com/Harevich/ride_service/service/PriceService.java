package com.Harevich.ride_service.service;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PriceService {

    BigDecimal getPriceByTwoAddresses(String from,
                                      String to,
                                      LocalDateTime currentTime);

}
