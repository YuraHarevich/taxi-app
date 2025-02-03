package com.Harevich.ride_service.service.impl;

import com.Harevich.ride_service.service.GeolocationService;
import com.Harevich.ride_service.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PriceServiceIml implements PriceService {
    private final GeolocationService geolocationService;

    @Override
    public BigDecimal getPriceByTwoAddresses(String s, String from) {
        return BigDecimal.ONE;
    }
}
