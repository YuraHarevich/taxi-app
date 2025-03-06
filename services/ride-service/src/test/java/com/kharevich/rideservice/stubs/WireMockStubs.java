package com.kharevich.rideservice.stubs;

import com.kharevich.rideservice.constants.TestConstants;
import com.kharevich.rideservice.sideservices.driver.DriverResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class WireMockStubs {

    public static void getDriverResponseStub(WireMockServer wireMockServer,
                                             ObjectMapper objectMapper,
                                             DriverResponse response) throws Exception {
        wireMockServer.stubFor(get(urlEqualTo(TestConstants.DRIVER_SERVICE_URL))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))));
    }

}
