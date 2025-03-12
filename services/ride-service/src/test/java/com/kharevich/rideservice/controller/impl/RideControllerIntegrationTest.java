package com.kharevich.rideservice.controller.impl;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.kharevich.rideservice.constants.RideServiceDTOFactory;
import com.kharevich.rideservice.constants.TestConstants;
import com.kharevich.rideservice.dto.response.RideResponse;
import com.kharevich.rideservice.model.enumerations.RideStatus;
import com.kharevich.rideservice.stubs.WireMockStubs;
import io.restassured.RestAssured;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.kharevich.rideservice.constants.RideFieldConstants.DRIVER_ID_FIELD;
import static com.kharevich.rideservice.constants.RideFieldConstants.PASSENGER_ID_FIELD;
import static com.kharevich.rideservice.constants.RideFieldConstants.RIDE_FROM_FIELD;
import static com.kharevich.rideservice.constants.RideFieldConstants.RIDE_ID_FIELD;
import static com.kharevich.rideservice.constants.RideFieldConstants.RIDE_STATUS_FIELD;
import static com.kharevich.rideservice.constants.RideFieldConstants.RIDE_TOTAL_ELEMENTS_FIELD;
import static com.kharevich.rideservice.constants.RideFieldConstants.RIDE_TO_FIELD;
import static com.kharevich.rideservice.constants.TestConstants.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@AutoConfigureWireMock(port = 9090)
@Sql(statements = {
        TestConstants.SQL_CLEAR_TABLES,
        TestConstants.SQL_INSERT_DATA
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class RideControllerIntegrationTest {
    @Container
    static PostgreSQLContainer psqlContainer = new PostgreSQLContainer(DockerImageName.parse("postgres:17.4"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", psqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", psqlContainer::getUsername);
        registry.add("spring.datasource.password", psqlContainer::getPassword);
    }

    @LocalServerPort
    private Integer port;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void applyForDriver_ValidRequest() throws Exception {
        WireMockStubs.getDriverResponseStub(wireMockServer, objectMapper, RideServiceDTOFactory.createDefaultDriverResponse());

        given()
                .contentType(ContentType.JSON)
                .queryParam(DRIVER_ID_FIELD,DEFAULT_DRIVER_ID)
                .when()
                .post(RIDE_SERVICE_APPLY_FOR_DRIVER)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void createOrderByPassenger_ValidRequest() throws Exception {
        WireMockStubs.getPassengerResponseStub(wireMockServer, objectMapper, RideServiceDTOFactory.createDefaultPassengerResponse());

        given()
                .contentType(ContentType.JSON)
                .body(RideServiceDTOFactory.createDefaultRideRequest())
                .when()
                .post(RIDE_SERVICE_CREATE_ORDER)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void createOrderByPassenger_InvalidRequest() throws Exception {
        WireMockStubs.getPassengerResponseStub(wireMockServer, objectMapper, RideServiceDTOFactory.createDefaultPassengerResponse());

        given()
                .contentType(ContentType.JSON)
                .body(RideServiceDTOFactory.createInvalidRideRequest())
                .when()
                .post(RIDE_SERVICE_CREATE_ORDER)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateRide_ValidRequest() throws Exception {
        WireMockStubs.getGeolocationCordinatesResponseStub(wireMockServer, objectMapper, GEOLOCATION_COORDINATES_RESPONSE_MAP);
        WireMockStubs.getGeolocationDirectionsResponseStub(wireMockServer, objectMapper, GEOLOCATION_DIRECTIONS_RESPONSE_MAP);

        var response = given()
                .contentType(ContentType.JSON)
                .queryParam(RIDE_ID_FIELD,DEFAULT_RIDE_ID)
                .body(RideServiceDTOFactory.createDefaultUpdateRideRequest())
                .when()
                .patch(RIDE_SERVICE_UPDATE_RIDE)
                .then()
                .statusCode(HttpStatus.ACCEPTED.value())
                .extract()
                .response();

        String actualFrom = response.jsonPath().getString("from");
        String actualTo = response.jsonPath().getString("to");
        assertEquals(actualFrom,MODIFIED_FROM_ADDRESS);
        assertEquals(actualTo,MODIFIED_TO_ADDRESS);
    }

    @Test
    public void updateRide_InvalidRequest() throws Exception {
        WireMockStubs.getGeolocationCordinatesResponseStub(wireMockServer, objectMapper, GEOLOCATION_COORDINATES_RESPONSE_MAP);
        WireMockStubs.getGeolocationDirectionsResponseStub(wireMockServer, objectMapper, GEOLOCATION_DIRECTIONS_RESPONSE_MAP);

        var response = given()
                .contentType(ContentType.JSON)
                .queryParam(RIDE_ID_FIELD,DEFAULT_RIDE_ID)
                .body(RideServiceDTOFactory.createInvalidUpdateRideRequest())
                .when()
                .patch(RIDE_SERVICE_UPDATE_RIDE)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createRide_ValidRequest() throws Exception {
        WireMockStubs.getGeolocationCordinatesResponseStub(wireMockServer, objectMapper, GEOLOCATION_COORDINATES_RESPONSE_MAP);
        WireMockStubs.getGeolocationDirectionsResponseStub(wireMockServer, objectMapper, GEOLOCATION_DIRECTIONS_RESPONSE_MAP);

        given()
                .contentType(ContentType.JSON)
                .queryParam(DRIVER_ID_FIELD, DEFAULT_DRIVER_ID)
                .body(RideServiceDTOFactory.createDefaultRideRequest())
                .when()
                .post(RIDE_SERVICE_CREATE_RIDE)
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void getRide_ValidRequest() throws Exception {
        var response = given()
                .contentType(ContentType.JSON)
                .queryParam(RIDE_ID_FIELD, DEFAULT_RIDE_ID)
                .when()
                .get(RIDE_SERVICE_CREATE_RIDE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        String actualFrom = response.jsonPath().getString(RIDE_FROM_FIELD);
        String actualTo = response.jsonPath().getString(RIDE_TO_FIELD);
        assertEquals(actualFrom, DEFAULT_FROM_ADDRESS);
        assertEquals(actualTo, DEFAULT_TO_ADDRESS);
    }

    @Test
    public void gGetAllRides_ValidRequest() throws Exception {
        var response = given()
                .contentType(ContentType.JSON)
                .queryParam(RIDE_ID_FIELD,DEFAULT_RIDE_ID)
                .when()
                .get(RIDE_SERVICE_GET_ALL_RIDES)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        String totalElements = response.jsonPath().getString(RIDE_TOTAL_ELEMENTS_FIELD);
        assertEquals(totalElements, EXPECTED_RIDE_TOTAL_ELEMENTS);
    }

    @Test
    public void getAllRidesByPassengerId_ValidRequest() throws Exception {
        WireMockStubs.getPassengerResponseStub(wireMockServer, objectMapper, RideServiceDTOFactory.createDefaultPassengerResponse());

        var response = given()
                .contentType(ContentType.JSON)
                .queryParam(PASSENGER_ID_FIELD, DEFAULT_PASSENGER_ID)
                .when()
                .get(RIDE_SERVICE_GET_ALL_RIDES_PASSENGER_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        String totalElements = response.jsonPath().getString(RIDE_TOTAL_ELEMENTS_FIELD);
        assertEquals(totalElements,"1");
    }

    @Test
    public void getAllRidesByDriverId_ValidRequest() throws Exception {
        WireMockStubs.getDriverResponseStub(wireMockServer, objectMapper, RideServiceDTOFactory.createDefaultDriverResponse());

        var response = given()
                .contentType(ContentType.JSON)
                .queryParam(DRIVER_ID_FIELD,DEFAULT_DRIVER_ID)
                .when()
                .get(RIDE_SERVICE_GET_ALL_RIDES_DRIVER_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        String totalElements = response.jsonPath().getString(RIDE_TOTAL_ELEMENTS_FIELD);
        assertEquals(totalElements,"1");
    }

    @Test
    public void changeStatusOfRide_ValidRequest() throws Exception {
        var response = given()
                .contentType(ContentType.JSON)
                .queryParam(RIDE_ID_FIELD, DEFAULT_RIDE_ID)
                .body(RideServiceDTOFactory.createDefaultUpdateRideRequest())
                .when()
                .patch(RIDE_SERVICE_CHANGE_RIDE_STATUS)
                .then()
                .statusCode(HttpStatus.ACCEPTED.value())
                .extract()
                .response();

        String actualRideStatus = response.jsonPath().getString(RIDE_STATUS_FIELD);
        assertTrue(Objects.equals(actualRideStatus, RideStatus.ACCEPTED.toString()) ||
                Objects.equals(actualRideStatus, RideStatus.DECLINED.toString()));
    }

}
