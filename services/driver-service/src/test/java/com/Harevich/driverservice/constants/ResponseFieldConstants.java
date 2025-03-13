package com.Harevich.driverservice.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseFieldConstants {

    public final static String DRIVER_NAME_FIELD = "name";
    public final static String DRIVER_SURNAME_FIELD = "surname";
    public final static String DRIVER_EMAIL_FIELD = "email";
    public final static String DRIVER_SEX_FIELD = "sex";
    public final static String DRIVER_ID_FIELD = "id";

    public final static String CAR_COLOR_FIELD = "color";
    public final static String CAR_NUMBER_FIELD = "number";
    public final static String CAR_BRAND_FIELD = "brand";
    public final static String CAR_ID_FIELD = "id";

    public final static String MERGE_TABLE_DRIVER_ID_FIELD = "driver_id";
    public final static String MERGE_TABLE_CAR_ID_FIELD = "car_id";

    public final static String PAGEABLE_RESPONSE_TOTAL_ELEMENTS_FIELD = "totalElements";

}
