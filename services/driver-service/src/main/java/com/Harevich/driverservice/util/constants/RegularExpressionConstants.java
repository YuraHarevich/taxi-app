package com.Harevich.driverservice.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegularExpressionConstants {
    public static final String PHONE_NUMBER_REGEX = "\\+375(29|44|25|33|17)\\d{7}";
    public static final String CAR_NUMBER_REGEX = "\\d{4} [A-Z]{2}-\\d{1}";
}
