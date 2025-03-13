package com.kharevich.ratingservice.controller.impl;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.kharevich.ratingservice.constants.RatingServiceDTOFactory;
import com.kharevich.ratingservice.stubs.WireMockStubs;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import io.restassured.RestAssured;

import java.util.UUID;

import static com.kharevich.ratingservice.constants.TestConstants.DEFAULT_DRIVER_ID;
import static com.kharevich.ratingservice.constants.TestConstants.DEFAULT_PASSENGER_ID;
import static com.kharevich.ratingservice.constants.TestConstants.EXPECTED_PERSONAL_RATING;
import static com.kharevich.ratingservice.constants.TestConstants.EXPECTED_TOTAL_ELEMENTS;
import static com.kharevich.ratingservice.constants.TestConstants.RATING_SERVICE_ESTIMATE_RIDE;
import static com.kharevich.ratingservice.constants.TestConstants.RATING_SERVICE_GET_ALL_RATINGS_DRIVER;
import static com.kharevich.ratingservice.constants.TestConstants.RATING_SERVICE_GET_ALL_RATINGS_PASSENGER;
import static com.kharevich.ratingservice.constants.TestConstants.RATING_SERVICE_GET_RATING_DRIVER;
import static com.kharevich.ratingservice.constants.TestConstants.RATING_SERVICE_GET_RATING_PASSENGER;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 9090)
public class RatingControllerIntegrationTests {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
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
    public void testGetPassengersRatingById() throws Exception {
        WireMockStubs.getPassengerResponseStub(wireMockServer, objectMapper, RatingServiceDTOFactory.createDefaultPassengerResponse());
        UUID id = UUID.fromString(DEFAULT_PASSENGER_ID);

        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("passengerId", id.toString())
                .when()
                .get(RATING_SERVICE_GET_RATING_PASSENGER)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        float totalRating = response.jsonPath().get("totalRating");

        assertEquals(EXPECTED_PERSONAL_RATING,totalRating);
    }

    @Test
    public void testGetDriversRatingById() throws Exception {
        WireMockStubs.getDriverResponseStub(wireMockServer, objectMapper, RatingServiceDTOFactory.createDefaultDriverResponse());
        UUID id = UUID.fromString(DEFAULT_DRIVER_ID);

        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("driverId", id.toString())
                .when()
                .get(RATING_SERVICE_GET_RATING_DRIVER)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        float totalRating = response.jsonPath().get("totalRating");

        assertEquals(EXPECTED_PERSONAL_RATING,totalRating);
    }

    @Test
    public void testGetAllDriversRatingById() throws Exception {
        WireMockStubs.getDriverResponseStub(wireMockServer, objectMapper, RatingServiceDTOFactory.createDefaultDriverResponse());
        UUID id = UUID.fromString(DEFAULT_DRIVER_ID);

        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("driverId", id.toString())
                .when()
                .get(RATING_SERVICE_GET_ALL_RATINGS_DRIVER)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        int totalElements = response.jsonPath().get("totalElements");

        assertEquals(EXPECTED_TOTAL_ELEMENTS,totalElements);
    }

    @Test
    public void testGetAllPassengersRatingById() throws Exception {
        WireMockStubs.getPassengerResponseStub(wireMockServer, objectMapper, RatingServiceDTOFactory.createDefaultPassengerResponse());
        UUID id = UUID.fromString(DEFAULT_PASSENGER_ID);

        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("passengerId", id.toString())
                .when()
                .get(RATING_SERVICE_GET_ALL_RATINGS_PASSENGER)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        int totalElements = response.jsonPath().get("totalElements");

        assertEquals(EXPECTED_TOTAL_ELEMENTS,totalElements);
    }

    @Test
    public void testEstimatePassenger() throws Exception {
        WireMockStubs.getRideResponseStub(wireMockServer, objectMapper, RatingServiceDTOFactory.createDefaultRideResponse());
        WireMockStubs.getPassengerResponseStub(wireMockServer, objectMapper, RatingServiceDTOFactory.createDefaultPassengerResponse());
        WireMockStubs.getDriverResponseStub(wireMockServer, objectMapper, RatingServiceDTOFactory.createDefaultDriverResponse());

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .body(RatingServiceDTOFactory.createDefaultRatingRequest())
                .post(RATING_SERVICE_ESTIMATE_RIDE)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .response();
    }

}
