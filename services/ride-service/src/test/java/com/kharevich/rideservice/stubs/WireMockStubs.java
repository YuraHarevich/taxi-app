package com.kharevich.rideservice.stubs;

import com.kharevich.rideservice.constants.TestConstants;
import com.kharevich.rideservice.sideservices.driver.DriverResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.kharevich.rideservice.sideservices.passenger.PassengerResponse;
import com.kharevich.rideservice.util.config.GeolocationParams;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.kharevich.rideservice.constants.TestConstants.DEFAULT_DRIVER_ID;
import static com.kharevich.rideservice.constants.TestConstants.GEOLOCATION_COORDINATES_SERVICE_URL;
import static com.kharevich.rideservice.constants.TestConstants.GEOLOCATION_DIRECTIONS_SERVICE_URL;

@RequiredArgsConstructor
public class WireMockStubs {

    private final GeolocationParams geolocationParams;

    public static void getDriverResponseStub(WireMockServer wireMockServer,
                                             ObjectMapper objectMapper,
                                             DriverResponse response) throws Exception {
        wireMockServer.stubFor(get(urlEqualTo(TestConstants.DRIVER_SERVICE_URL))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))));
    }

    public static void getPassengerResponseStub(WireMockServer wireMockServer,
                                             ObjectMapper objectMapper,
                                             PassengerResponse response) throws Exception {
        wireMockServer.stubFor(get(urlEqualTo(TestConstants.PASSENGER_SERVICE_URL))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))));
    }



    public static void getGeolocationCordinatesResponseStub(WireMockServer wireMockServer,
                                             ObjectMapper objectMapper,
                                             Map<String,Object> response) throws Exception {
        wireMockServer.stubFor(get(urlPathEqualTo(GEOLOCATION_COORDINATES_SERVICE_URL))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))));
    }

    public static void getGeolocationDirectionsResponseStub(WireMockServer wireMockServer,
                                                            ObjectMapper objectMapper,
                                                            Map<String,Object> response) throws Exception {
        wireMockServer.stubFor(get(urlPathEqualTo(GEOLOCATION_DIRECTIONS_SERVICE_URL))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))));
    }

}
