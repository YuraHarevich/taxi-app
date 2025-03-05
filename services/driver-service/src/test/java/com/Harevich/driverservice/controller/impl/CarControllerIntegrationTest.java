package com.Harevich.driverservice.controller.impl;

import com.Harevich.driverservice.constants.CarRequestFactory;
import com.Harevich.driverservice.dto.request.CarRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import java.util.UUID;

import static com.Harevich.driverservice.constants.TestConstants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Sql(statements = {
        SQL_CLEAR_MERGE_TABLE,
        SQL_CLEAR_TABLE_CARS,
        SQL_INSERT_DATA_CAR,
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
public class CarControllerIntegrationTest {
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

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void testCreateCar() {
        CarRequest request = CarRequestFactory.createDefaultRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(RELATIVE_CREATE_CAR_URL)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("color", equalTo(request.color()))
                .body("number", equalTo(request.number()))
                .body("brand", equalTo(request.brand()));
    }

    @Test
    public void testCreateCar_InvalidNumber() {
        CarRequest invalidRequest = CarRequestFactory.createInvalidNumberRequest();

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequest)
                .when()
                .post(RELATIVE_CREATE_CAR_URL)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testCreateCar_EmptyFields() {
        CarRequest invalidRequest = CarRequestFactory.createInvalidRequest();

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequest)
                .when()
                .post(RELATIVE_CREATE_CAR_URL)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testUpdateCar() {
        CarRequest request = CarRequestFactory.createUpdateRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .queryParam("id", BASIC_CAR_UUID)
                .when()
                .patch(RELATIVE_UPDATE_CAR_URL)
                .then()
                .statusCode(HttpStatus.ACCEPTED.value())
                .body("color", equalTo(request.color()))
                .body("number", equalTo(request.number()))
                .body("brand", equalTo(request.brand()));
    }

    @Test
    public void testUpdateCar_RepeatedNumber() {
        CarRequest invalidRequest = CarRequestFactory.createRepeatedNumberRequest();

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequest)
                .when()
                .patch(RELATIVE_UPDATE_CAR_URL)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testGetCarById() {
        given()
                .queryParam("id", BASIC_CAR_UUID)
                .when()
                .get(RELATIVE_GET_ID_CAR_URL)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testGetAllAvailableCarById() {
        given()
                .queryParam(BASIC_CAR_UUID)
                .when()
                .get(RELATIVE_GET_AVAILABLE_CARS_URL)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("totalElements",equalTo(1));
    }

    @Test
    public void testGetCarByNumber() {
        given()
                .queryParam("number", BASIC_CAR_NUMBER)
                .when()
                .get(RELATIVE_GET_NUMBER_CAR_URL)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testDeleteCarById_NotFound() {
        UUID nonExistentId = UUID.randomUUID();
        given()
                .queryParam("id", nonExistentId.toString())
                .when()
                .delete(RELATIVE_DELETE_CAR_URL)
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    public void testDeleteCarById() {
        given()
                .queryParam("id", BASIC_CAR_UUID)
                .when()
                .delete(RELATIVE_DELETE_CAR_URL)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}
