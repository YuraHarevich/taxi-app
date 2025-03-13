package com.kharevich.ratingservice.stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.kharevich.ratingservice.constants.TestConstants;
import com.kharevich.ratingservice.sideservices.driver.DriverResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.kharevich.ratingservice.sideservices.passenger.PassengerResponse;
import com.kharevich.ratingservice.sideservices.ride.RideResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

public class WireMockStubs {

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

    public static void getRideResponseStub(WireMockServer wireMockServer,
                                                ObjectMapper objectMapper,
                                                RideResponse response) throws Exception {
        wireMockServer.stubFor(get(urlEqualTo(TestConstants.RIDE_SERVICE_URL))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))));
    }

}
