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

import java.util.UUID;

import static com.Harevich.driverservice.constants.TestConstants.BASIC_SERVER_HOST;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_CREATE_CAR_URL;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_CREATE_DRIVER_URL;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_DELETE_CAR_URL;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_GET_ID_CAR_URL;
import static com.Harevich.driverservice.constants.TestConstants.RELATIVE_UPDATE_CAR_URL;


@Transactional
public class CarStepDefinitions {

    private Response response;
    private String requestBody;
    private static UUID carId;

    @Given("There is car service request")
    public void thereIsCarServiceRequest(String body) {
        this.requestBody = body;
    }

    @When("creates the car")
    public void createsTheCar() {
        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(BASIC_SERVER_HOST + RELATIVE_CREATE_CAR_URL);

        if (response.getStatusCode() == 201) {
            carId = UUID.fromString(response.jsonPath().getString("id"));
        }
    }

    @When("updates the car with id")
    public void updatesTheCarWithId() {
        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .param("id", carId)
                .patch(BASIC_SERVER_HOST + RELATIVE_UPDATE_CAR_URL);
    }

    @When("updates the car with id {string}")
    public void updatesTheCarWithId(String id) {
        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .param("id", UUID.fromString(id))
                .patch(BASIC_SERVER_HOST + RELATIVE_UPDATE_CAR_URL);
    }

    @When("retrieves the car by id")
    public void retrievesTheCarById() {
        response = RestAssured.given()
                .param("id", carId)
                .get(BASIC_SERVER_HOST + RELATIVE_GET_ID_CAR_URL);
    }

    @When("retrieves the car with id {string}")
    public void retrievesTheCarWithId(String id) {
        response = RestAssured.given()
                .param("id", id)
                .get(BASIC_SERVER_HOST + RELATIVE_GET_ID_CAR_URL);
    }

    @When("deletes the car by id")
    public void deletesTheCarById() {
        response = RestAssured.given()
                .param("id", carId)
                .delete(BASIC_SERVER_HOST + RELATIVE_DELETE_CAR_URL);
    }

    @When("deletes the car with id {string}")
    public void deletesTheCarWithId(String id) {
        response = RestAssured.given()
                .param("id", id)
                .delete(BASIC_SERVER_HOST + RELATIVE_DELETE_CAR_URL);
    }

    @Then("return car response status {int}")
    public void returnCarResponseStatus(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @And("the car should no longer exist")
    public void theCarShouldNoLongerExist() {
        Response getResponse = RestAssured.given()
                .param("id", carId)
                .get(BASIC_SERVER_HOST + RELATIVE_GET_ID_CAR_URL);

        Assert.assertEquals(404, getResponse.getStatusCode());
    }

    @And("car response body should contain")
    public void responseBodyShouldContain(String expectedResponse) {
        JsonPath actualResponse = response.jsonPath();
        JsonPath expectedJson = new JsonPath(expectedResponse);

        expectedJson.getMap("").forEach((key, value) -> {
            if (!key.equals("id") && !key.equals("driverId")) {
                Assert.assertEquals(value, actualResponse.get(key.toString()));
            }
        });
    }
}
