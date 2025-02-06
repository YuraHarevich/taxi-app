package com.Harevich.ride_service.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeTariffConstantValues {

    public static final LocalTime MORNING_PEAK_START = LocalTime.of(7, 0);

    public static final LocalTime MORNING_PEAK_END = LocalTime.of(8, 59);

    public static final LocalTime EVENING_PEAK_START = LocalTime.of(17, 0);

    public static final LocalTime EVENING_PEAK_END = LocalTime.of(18, 59);

    public static final Integer MINUTES_IN_HOUR_WITHOUT_INCREASED_TARIFF = 50;

    public static final double TARIFF_PER_KM = 1;

}
