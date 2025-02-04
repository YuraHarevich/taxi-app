package com.Harevich.ride_service.service.impl;

import com.Harevich.ride_service.service.GeolocationService;
import com.Harevich.ride_service.service.PriceService;
import com.Harevich.ride_service.util.constants.TariffMultiplyConstants;
import com.Harevich.ride_service.util.constants.TimeTariffConstantValues;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class PriceServiceIml implements PriceService {
    private final GeolocationService geolocationService;

    @Override
    public BigDecimal getPriceByTwoAddresses(String from, String to, LocalDateTime currentTime) {
        double distance = geolocationService.getRouteDistanceByTwoAddresses(from,to);
        BigDecimal price = BigDecimal.valueOf(distance);
        //не знаю, вроде криво, но иначе будет еще более криво
        BigDecimal finalPrice =
                increaseTariffByMorningPeak(currentTime,
                    increaseTariffByEveningPeak(currentTime,
                        increaseTariffByTheEndOfHour(currentTime,price)));
        return finalPrice;
    }

    private BigDecimal increaseTariffByMorningPeak(LocalDateTime currentTime, BigDecimal price){
        LocalTime orderTime = currentTime.toLocalTime();
        if (orderTime.isAfter(TimeTariffConstantValues.MORNING_PEAK_START) && orderTime.isBefore(TimeTariffConstantValues.MORNING_PEAK_END))
            price.multiply(BigDecimal.valueOf(TariffMultiplyConstants.MORNING_MULTIPLY_CONSTANT));
        return price;
    }
    private BigDecimal increaseTariffByEveningPeak(LocalDateTime currentTime, BigDecimal price){
        LocalTime orderTime = currentTime.toLocalTime();
        if (orderTime.isAfter(TimeTariffConstantValues.EVENING_PEAK_START) && orderTime.isBefore(TimeTariffConstantValues.EVENING_PEAK_END))
            price.multiply(BigDecimal.valueOf(TariffMultiplyConstants.EVENING_MULTIPLY_CONSTANT));
        return price;
    }
    private BigDecimal increaseTariffByTheEndOfHour(LocalDateTime currentTime, BigDecimal price){
        LocalTime orderTime = currentTime.toLocalTime();
        int minute = orderTime.getMinute();
        if (minute >= TimeTariffConstantValues.MINUTES_IN_HOUR_WITHOUT_INCREASED_TARIFF) {
            price = price.multiply(BigDecimal.valueOf(TariffMultiplyConstants.END_OF_HOUR_MULTIPLY_CONSTANT));
            price = price.setScale(2, RoundingMode.HALF_UP);
        }
        return price;
    }
}
