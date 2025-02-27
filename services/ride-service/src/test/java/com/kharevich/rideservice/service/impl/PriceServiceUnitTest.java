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
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.kharevich.rideservice.service.GeolocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.kharevich.rideservice.service.impl.PriceServiceIml;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;

@ExtendWith(MockitoExtension.class)
public class PriceServiceUnitTest {

    @Mock
    private GeolocationService geolocationService;

    @InjectMocks
    private PriceServiceIml priceService;

    private double EVENING_MULTIPLY_CONSTANT;

    private double MORNING_MULTIPLY_CONSTANT;

    private double END_OF_HOUR_MULTIPLY_CONSTANT;

    private double distance;

    private BigDecimal defaultPrice;


    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(priceService, "zoneId", "Europe/Minsk");
        EVENING_MULTIPLY_CONSTANT = 1.3;
        MORNING_MULTIPLY_CONSTANT = 1.3;
        END_OF_HOUR_MULTIPLY_CONSTANT = 1.2;
        distance = 10.0;
        defaultPrice = BigDecimal.valueOf(distance * TARIFF_PER_KM);
    }

    @Test
    public void testGetPriceByTwoAddresses_NoTariffIncrease() {
        String from = "From";
        String to = "To";
        LocalDateTime currentTime = LocalDateTime.of(2024, 10, 1, 12, 1);

        when(geolocationService.getRouteDistanceByTwoAddresses(from, to)).thenReturn(distance);

        BigDecimal result = priceService.getPriceByTwoAddresses(from, to, currentTime);

        assertEquals(defaultPrice, result);
        verify(geolocationService).getRouteDistanceByTwoAddresses(from, to);
    }

    @Test
    public void testGetPriceByTwoAddresses_MorningPeak() {
        String from = "From";
        String to = "To";
        LocalDateTime currentTime = LocalDateTime.now();
        LocalTime localTime = currentTime.toLocalTime();
        BigDecimal increased = defaultPrice;

        when(geolocationService.getRouteDistanceByTwoAddresses(from, to)).thenReturn(distance);

        if (localTime.isAfter(MORNING_PEAK_START) && localTime.isBefore(MORNING_PEAK_END)) {
            increased = defaultPrice.multiply(BigDecimal.valueOf(MORNING_MULTIPLY_CONSTANT));
        }

        BigDecimal result = priceService.getPriceByTwoAddresses(from, to, currentTime);

        assertEquals(increased, result);
        verify(geolocationService).getRouteDistanceByTwoAddresses(from, to);
    }

    @Test
    public void testGetPriceByTwoAddresses_EveningPeak() {
        String from = "From";
        String to = "To";
        LocalDateTime currentTime = LocalDateTime.now();
        LocalTime localTime = currentTime.toLocalTime();
        BigDecimal increased = defaultPrice;

        when(geolocationService.getRouteDistanceByTwoAddresses(from, to)).thenReturn(distance);

        if (localTime.isAfter(EVENING_PEAK_START) && localTime.isBefore(EVENING_PEAK_END)) {
            increased = defaultPrice.multiply(BigDecimal.valueOf(EVENING_MULTIPLY_CONSTANT));
        }
        BigDecimal result = priceService.getPriceByTwoAddresses(from, to, currentTime);

        assertEquals(increased, result);
        verify(geolocationService).getRouteDistanceByTwoAddresses(from, to);
    }

    @Test
    public void testGetPriceByTwoAddresses_EndOfHour() {
        String from = "From";
        String to = "To";
        LocalDateTime currentTime = LocalDateTime.now();
        BigDecimal increased = defaultPrice;

        when(geolocationService.getRouteDistanceByTwoAddresses(from, to)).thenReturn(distance);

        int minute = currentTime.getMinute();
        if (minute >= MINUTES_IN_HOUR_WITHOUT_INCREASED_TARIFF) {
            increased = defaultPrice.multiply(BigDecimal.valueOf(END_OF_HOUR_MULTIPLY_CONSTANT));
            increased = increased.setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal result = priceService.getPriceByTwoAddresses(from, to, currentTime);

        assertEquals(increased, result);
        verify(geolocationService).getRouteDistanceByTwoAddresses(from, to);
    }

}