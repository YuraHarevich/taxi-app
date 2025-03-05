package com.Harevich.driverservice.e2e;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

import static com.Harevich.driverservice.constants.TestConstants.BASIC_SERVER_HOST;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_CREATE_DRIVER_URL;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_DELETE_DRIVER_URL;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_GET_ID_DRIVER_URL;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_UPDATE_DRIVER_URL;

@Transactional
public class DriverStepDefinitions {

    private Response response;
    private String requestBody;
    private static UUID driverId;
    private static UUID carId;

    @Given("There is driver service request")
    public void thereIsDriverServiceRequest(String body) {
        this.requestBody = body;
    }

    @When("creates the driver")
    public void createsTheDriver() {
        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(BASIC_SERVER_HOST + RELATIVE_CREATE_DRIVER_URL);

        if (response.getStatusCode() == 201) {
            driverId = UUID.fromString(response.jsonPath().getString("id"));
        }
    }

    @When("updates the driver with id")
    public void updatesTheDriverWithId() {
        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .param("id", driverId)
                .patch(BASIC_SERVER_HOST + RELATIVE_UPDATE_DRIVER_URL);
    }

    @When("updates the driver with id {string}")
    public void updatesTheDriverWithId(String id) {
        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .param("id", UUID.fromString(id))
                .patch(BASIC_SERVER_HOST+RELATIVE_UPDATE_DRIVER_URL);
    }

    @When("retrieves the driver by id")
    public void retrievesTheDriverById() {
        response = RestAssured.given()
                .param("id", driverId)
                .get(BASIC_SERVER_HOST + RELATIVE_GET_ID_DRIVER_URL);
    }

    @When("retrieves the driver with id {string}")
    public void retrieves_the_driver_with_id(String id) {
        response = RestAssured.given()
                .param("id", id )
                .get(BASIC_SERVER_HOST + RELATIVE_GET_ID_DRIVER_URL);
    }


    @When("deletes the driver by id")
    public void deletesTheDriverById() {
        response = RestAssured.given()
                .param("id", driverId)
                .delete(BASIC_SERVER_HOST + RELATIVE_DELETE_DRIVER_URL);
    }

    @When("deletes the driver with id {string}")
    public void deletesTheDriverWithId(String id) {
        response = RestAssured.given()
                .param("id", id)
                .delete(BASIC_SERVER_HOST + RELATIVE_DELETE_DRIVER_URL);
    }

    @Then("return driver response status {int}")
    public void returnResponseStatus(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @And("driver response body should contain")
    public void responseBodyShouldContain(String expectedResponse) {
        JsonPath actualResponse = response.jsonPath();
        JsonPath expectedJson = new JsonPath(expectedResponse);

        expectedJson.getMap("").forEach((key, value) -> {
            if (!key.equals("id") && !key.equals("carId")) {
                Assert.assertEquals(value, actualResponse.get(key.toString()));
            }
        });
    }

    @And("response should contain error message")
    public void responseShouldContainErrorMessage(String expectedResponse) {
        JsonPath actualResponse = response.jsonPath();
        JsonPath expectedJson = new JsonPath(expectedResponse);

        expectedJson.getMap("").forEach((key, value) -> {
            Assert.assertEquals(value, actualResponse.get(key.toString()));
        });
    }

    @And("the driver should no longer exist")
    public void theDriverShouldNoLongerExist() {
        Response getResponse = RestAssured.given()
                .param("id", driverId)
                .get(BASIC_SERVER_HOST + RELATIVE_GET_ID_DRIVER_URL);

        Assert.assertEquals(404, getResponse.getStatusCode());
    }
}