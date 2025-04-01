package com.kharevich.ratingservice.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RatingLogMessagesTemplate {

    public static final String HTTP_REQUEST_LOGGING_MESSAGE = "Http request | Method: {} | URI: {} | Args -> {}";

    public static final String HTTP_RESPONSE_LOGGING_MESSAGE = "Http response | Method: {} | URI: {} | Args -> {}";

    public static final String ERROR_SERIALIZING_JSON_MESSAGE = "Error serializing object to JSON";

}
