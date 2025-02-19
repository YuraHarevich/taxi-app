package com.kharevich.rideservice.service.impl;

import static com.kharevich.rideservice.util.constants.TariffMultiplyConstants.END_OF_HOUR_MULTIPLY_CONSTANT;
import static com.kharevich.rideservice.util.constants.TariffMultiplyConstants.EVENING_MULTIPLY_CONSTANT;
import static com.kharevich.rideservice.util.constants.TariffMultiplyConstants.MORNING_MULTIPLY_CONSTANT;
import static com.kharevich.rideservice.util.constants.TimeTariffConstantValues.EVENING_PEAK_END;
import static com.kharevich.rideservice.util.constants.TimeTariffConstantValues.EVENING_PEAK_START;
import static com.kharevich.rideservice.util.constants.TimeTariffConstantValues.MINUTES_IN_HOUR_WITHOUT_INCREASED_TARIFF;
import static com.kharevich.rideservice.util.constants.TimeTariffConstantValues.MORNING_PEAK_END;
import static com.kharevich.rideservice.util.constants.TimeTariffConstantValues.MORNING_PEAK_START;
import static com.kharevich.rideservice.util.constants.TimeTariffConstantValues.TARIFF_PER_KM;

import com.kharevich.rideservice.service.GeolocationService;
import com.kharevich.rideservice.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class PriceServiceIml implements PriceService {

    @Value("${app.time.zoneId}")
    private String zoneId;

    private final GeolocationService geolocationService;

    @Override
    public BigDecimal getPriceByTwoAddresses(String from, String to, LocalDateTime currentTime) {
        double distance = geolocationService.getRouteDistanceByTwoAddresses(from, to);
        BigDecimal price = BigDecimal.valueOf(distance * TARIFF_PER_KM);
        BigDecimal finalPrice =
                increaseTariffByMorningPeak(currentTime,
                    increaseTariffByEveningPeak(currentTime,
                        increaseTariffByTheEndOfHour(currentTime, price)));
        return finalPrice;
    }

    private BigDecimal increaseTariffByMorningPeak(LocalDateTime currentTime, BigDecimal price){
        ZonedDateTime minskTime = currentTime.atZone(ZoneId.of(zoneId));
        LocalTime orderTime = minskTime.toLocalTime();

        if (orderTime.isAfter(MORNING_PEAK_START) && orderTime.isBefore(MORNING_PEAK_END)) {
            price.multiply(BigDecimal.valueOf(MORNING_MULTIPLY_CONSTANT));
        }
        return price;
    }

    private BigDecimal increaseTariffByEveningPeak(LocalDateTime currentTime, BigDecimal price){
        ZonedDateTime minskTime = currentTime.atZone(ZoneId.of(zoneId));
        LocalTime orderTime = minskTime.toLocalTime();

        if (orderTime.isAfter(EVENING_PEAK_START) && orderTime.isBefore(EVENING_PEAK_END)) {
            price.multiply(BigDecimal.valueOf(EVENING_MULTIPLY_CONSTANT));
        }
        return price;
    }

    private BigDecimal increaseTariffByTheEndOfHour(LocalDateTime currentTime, BigDecimal price){
        ZonedDateTime minskTime = currentTime.atZone(ZoneId.of(zoneId));
        LocalTime orderTime = minskTime.toLocalTime();

        int minute = orderTime.getMinute();
        if (minute >= MINUTES_IN_HOUR_WITHOUT_INCREASED_TARIFF) {
            price = price.multiply(BigDecimal.valueOf(END_OF_HOUR_MULTIPLY_CONSTANT));
            price = price.setScale(2, RoundingMode.HALF_UP);
        }
        return price;
    }

}
