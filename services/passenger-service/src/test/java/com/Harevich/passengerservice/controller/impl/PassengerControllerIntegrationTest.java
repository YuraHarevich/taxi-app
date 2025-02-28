package com.Harevich.passengerservice.controller.impl;

import com.Harevich.passengerservice.constants.PassengerRequestFactory;
import com.Harevich.passengerservice.dto.PassengerRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import com.Harevich.passengerservice.constants.TestConstants;

import java.util.UUID;

import static com.Harevich.passengerservice.constants.TestConstants.BASIC_UUID;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Sql(statements = {
        TestConstants.SQL_CLEAR_TABLE,
        TestConstants.SQL_INSERT_DATA
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PassengerControllerIntegrationTest {

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
    public void testCreatePassenger() {
        PassengerRequest request = PassengerRequestFactory.createDefaultRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/v1/passengers")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("email", equalTo(request.email()))
                .body("name", equalTo(request.name()))
                .body("surname", equalTo(request.surname()))
                .body("number", equalTo(request.number()));
    }

    @Test
    public void testCreatePassenger_InvalidEmail() {
        PassengerRequest invalidRequest = PassengerRequestFactory.createInvalidEmailRequest();

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequest)
                .when()
                .post("/api/v1/passengers")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testCreatePassenger_InvalidPhoneNumber() {
        PassengerRequest invalidRequest = PassengerRequestFactory.createInvalidPhoneRequest();

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequest)
                .when()
                .post("/api/v1/passengers")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testCreatePassenger_EmptyFields() {
        PassengerRequest invalidRequest = PassengerRequestFactory.createInvalidRequest();

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequest)
                .when()
                .post("/api/v1/passengers")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testUpdatePassenger() {
        PassengerRequest request = PassengerRequestFactory.createUpdateRequest();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .queryParam("id", BASIC_UUID)
                .when()
                .patch("/api/v1/passengers")
                .then()
                .statusCode(HttpStatus.ACCEPTED.value())
                .body("email", equalTo(request.email()))
                .body("name", equalTo(request.name()))
                .body("surname", equalTo(request.surname()))
                .body("number", equalTo(request.number()));
    }

    @Test
    public void testUpdatePassenger_RepeatedPhoneNumber() {
        PassengerRequest invalidRequest = PassengerRequestFactory.createRepeatedEmailRequest();

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequest)
                .when()
                .patch("/api/v1/passengers")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testGetPassengerById() {
        UUID id = UUID.fromString(BASIC_UUID);

        given()
                .queryParam("id", id.toString())
                .when()
                .get("/api/v1/passengers")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testDeletePassengerById_NotFound() {
        UUID nonExistentId = UUID.randomUUID();

        given()
                .queryParam("id", nonExistentId.toString())
                .when()
                .delete("/api/v1/passengers")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testDeletePassengerById() {
        UUID id = UUID.fromString("cf72326f-ef5e-47e2-8d40-d850e1ccd358");

        given()
                .queryParam("id", id.toString())
                .when()
                .delete("/api/v1/passengers")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}