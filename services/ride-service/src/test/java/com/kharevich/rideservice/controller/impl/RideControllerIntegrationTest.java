package com.kharevich.rideservice.controller.impl;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.kharevich.rideservice.constants.RideServiceResponseFactory;
import com.kharevich.rideservice.constants.TestConstants;
import com.kharevich.rideservice.stubs.WireMockStubs;
import io.restassured.RestAssured;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static com.kharevich.rideservice.constants.TestConstants.DEFAULT_DRIVER_ID;
import static com.kharevich.rideservice.constants.TestConstants.RIDE_SERVICE_APPLY_FOR_DRIVER;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Sql(statements = {
        TestConstants.SQL_CLEAR_TABLE,
        TestConstants.SQL_INSERT_DATA
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
public class RideControllerIntegrationTest {
    @Container
    static PostgreSQLContainer psqlContainer = new PostgreSQLContainer(DockerImageName.parse("postgres:latest"));

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
    public void testApplyForDriver() throws Exception {
        WireMockStubs.getDriverResponseStub(wireMockServer, objectMapper, RideServiceResponseFactory.createDefaultDriverResponse());
        given()
                .contentType(ContentType.JSON)
                .param("driver_id", DEFAULT_DRIVER_ID)
                .when()
                .post(RIDE_SERVICE_APPLY_FOR_DRIVER)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}
