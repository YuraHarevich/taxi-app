package com.Harevich.driverservice.controller.impl;

import com.Harevich.driverservice.constants.DriverRequestFactory;
import com.Harevich.driverservice.dto.request.DriverRequest;
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

import static com.Harevich.driverservice.constants.TestConstants.BASIC_CAR_UUID;
import static com.Harevich.driverservice.constants.TestConstants.BASIC_DRIVER_UUID;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_ASSIGN_CAR_TO_DRIVER_URL;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_CREATE_DRIVER_URL;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_DELETE_DRIVER_URL;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_GET_ID_DRIVER_URL;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_UPDATE_DRIVER_URL;
import static com.Harevich.driverservice.constants.TestConstants.SQL_CLEAR_MERGE_TABLE;
import static com.Harevich.driverservice.constants.TestConstants.SQL_CLEAR_TABLE_CARS;
import static com.Harevich.driverservice.constants.TestConstants.SQL_CLEAR_TABLE_DRIVERS;
import static com.Harevich.driverservice.constants.TestConstants.SQL_INSERT_DATA_CAR;
import static com.Harevich.driverservice.constants.TestConstants.SQL_INSERT_DATA_CAR_DRIVER_MERGE;
import static com.Harevich.driverservice.constants.TestConstants.SQL_INSERT_DATA_DRIVER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Sql(statements = {
        SQL_CLEAR_MERGE_TABLE,
        SQL_CLEAR_TABLE_CARS,
        SQL_CLEAR_TABLE_DRIVERS,
        SQL_INSERT_DATA_CAR,
        SQL_INSERT_DATA_DRIVER,
        SQL_INSERT_DATA_CAR_DRIVER_MERGE
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
public class DriverControllerIntegrationTest {
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
    public void createDriver_ValidRequest() {
        DriverRequest request = DriverRequestFactory.createDefaultRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(RELATIVE_CREATE_DRIVER_URL)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo(request.name()))
                .body("surname", equalTo(request.surname()))
                .body("email", equalTo(request.email()))
                .body("sex", equalTo(request.sex()));;
    }

    @Test
    public void createDriver_InvalidNumber() {
        DriverRequest invalidRequest = DriverRequestFactory.createInvalidNumberRequest();

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequest)
                .when()
                .post(RELATIVE_CREATE_DRIVER_URL)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createDriver_EmptyFields() {
        DriverRequest invalidRequest = DriverRequestFactory.createInvalidRequest();

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequest)
                .when()
                .post(RELATIVE_CREATE_DRIVER_URL)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateDriver_ValidRequest() {
        DriverRequest request = DriverRequestFactory.createUpdateRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .queryParam("id", BASIC_DRIVER_UUID)
                .when()
                .patch(RELATIVE_UPDATE_DRIVER_URL)
                .then()
                .statusCode(HttpStatus.ACCEPTED.value())
                .body("name", equalTo(request.name()))
                .body("surname", equalTo(request.surname()))
                .body("email", equalTo(request.email()))
                .body("sex", equalTo(request.sex()));
    }

    @Test
    public void updateDriver_RepeatedNumber() {
        DriverRequest invalidRequest = DriverRequestFactory.createRepeatedNumberRequest();

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequest)
                .when()
                .patch(RELATIVE_UPDATE_DRIVER_URL)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateDriver_RepeatedEmail() {
        DriverRequest invalidRequest = DriverRequestFactory.createRepeatedEmailRequest();

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequest)
                .when()
                .patch(RELATIVE_UPDATE_DRIVER_URL)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getDriverById_ValidRequest() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("id", BASIC_DRIVER_UUID)
                .when()
                .get(RELATIVE_GET_ID_DRIVER_URL)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void assignPersonalCar_ValidRequest() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("driver_id", BASIC_DRIVER_UUID)
                .queryParam("car_id", BASIC_CAR_UUID)
                .when()
                .patch(RELATIVE_ASSIGN_CAR_TO_DRIVER_URL)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deleteDriverById_NotFound() {
        UUID nonExistentId = UUID.randomUUID();
        given()
                .contentType(ContentType.JSON)
                .queryParam("id", nonExistentId.toString())
                .when()
                .delete(RELATIVE_DELETE_DRIVER_URL)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deleteDriverById_ValidRequest() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("id", BASIC_DRIVER_UUID)
                .when()
                .delete(RELATIVE_DELETE_DRIVER_URL)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}
