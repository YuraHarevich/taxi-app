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

import static com.Harevich.driverservice.constants.ResponseFieldConstants.CAR_BRAND_FIELD;
import static com.Harevich.driverservice.constants.ResponseFieldConstants.CAR_COLOR_FIELD;
import static com.Harevich.driverservice.constants.ResponseFieldConstants.CAR_ID_FIELD;
import static com.Harevich.driverservice.constants.ResponseFieldConstants.CAR_NUMBER_FIELD;
import static com.Harevich.driverservice.constants.ResponseFieldConstants.PAGEABLE_RESPONSE_TOTAL_ELEMENTS_FIELD;
import static com.Harevich.driverservice.constants.TestConstants.BASIC_CAR_NUMBER;
import static com.Harevich.driverservice.constants.TestConstants.BASIC_CAR_UUID;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_CREATE_CAR_URL;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_DELETE_CAR_URL;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_GET_AVAILABLE_CARS_URL;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_GET_ID_CAR_URL;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_GET_NUMBER_CAR_URL;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_UPDATE_CAR_URL;
import static com.Harevich.driverservice.constants.TestConstants.SQL_CLEAR_MERGE_TABLE;
import static com.Harevich.driverservice.constants.TestConstants.SQL_CLEAR_TABLE_CARS;
import static com.Harevich.driverservice.constants.TestConstants.SQL_INSERT_DATA_CAR;
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
    static PostgreSQLContainer psqlContainer = new PostgreSQLContainer(DockerImageName.parse("postgres:17.4"));

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
    public void createCar_ValidRequest() {
        CarRequest request = CarRequestFactory.createDefaultRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(RELATIVE_CREATE_CAR_URL)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body(CAR_COLOR_FIELD, equalTo(request.color()))
                .body(CAR_NUMBER_FIELD, equalTo(request.number()))
                .body(CAR_BRAND_FIELD, equalTo(request.brand()));
    }

    @Test
    public void createCar_InvalidNumber() {
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
    public void createCar_EmptyFields() {
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
    public void updateCar_ValidRequest() {
        CarRequest request = CarRequestFactory.createUpdateRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .queryParam(CAR_ID_FIELD, BASIC_CAR_UUID)
                .when()
                .patch(RELATIVE_UPDATE_CAR_URL)
                .then()
                .statusCode(HttpStatus.ACCEPTED.value())
                .body(CAR_COLOR_FIELD, equalTo(request.color()))
                .body(CAR_NUMBER_FIELD, equalTo(request.number()))
                .body(CAR_BRAND_FIELD, equalTo(request.brand()));
    }

    @Test
    public void updateCar_RepeatedNumber() {
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
    public void getCarById_ValidRequest() {
        given()
                .contentType(ContentType.JSON)
                .queryParam(CAR_ID_FIELD, BASIC_CAR_UUID)
                .when()
                .get(RELATIVE_GET_ID_CAR_URL)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getAllAvailableCarById_ValidRequest() {
        given()
                .contentType(ContentType.JSON)
                .queryParam(BASIC_CAR_UUID)
                .when()
                .get(RELATIVE_GET_AVAILABLE_CARS_URL)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(PAGEABLE_RESPONSE_TOTAL_ELEMENTS_FIELD,equalTo(1));
    }

    @Test
    public void getCarByNumber_ValidRequest() {
        given()
                .contentType(ContentType.JSON)
                .queryParam(CAR_NUMBER_FIELD, BASIC_CAR_NUMBER)
                .when()
                .get(RELATIVE_GET_NUMBER_CAR_URL)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deleteCarById_NotFound() {
        UUID nonExistentId = UUID.randomUUID();
        given()
                .contentType(ContentType.JSON)
                .queryParam(CAR_ID_FIELD, nonExistentId.toString())
                .when()
                .delete(RELATIVE_DELETE_CAR_URL)
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    public void deleteCarById_ValidRequest() {
        given()
                .contentType(ContentType.JSON)
                .queryParam(CAR_ID_FIELD, BASIC_CAR_UUID)
                .when()
                .delete(RELATIVE_DELETE_CAR_URL)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}
