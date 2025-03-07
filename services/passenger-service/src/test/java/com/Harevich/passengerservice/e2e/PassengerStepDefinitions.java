package com.Harevich.passengerservice.e2e;

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

import static com.Harevich.passengerservice.constants.TestConstants.BASE_PASSENGERS_URL;

@Transactional
public class PassengerStepDefinitions {

    private Response response;
    private String requestBody;
    private static UUID passengerId;

    @Given("There is passenger service request")
    public void thereIsPassengerServiceRequest(String body) {
        this.requestBody = body;
    }

    @When("creates the passenger")
    public void createsThePassenger() {
        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(BASE_PASSENGERS_URL);

        if (response.getStatusCode() == 201) {
            passengerId = UUID.fromString(response.jsonPath().getString("id"));
        }
    }

    @When("updates the passenger with id")
    public void updatesThePassengerWithId() {
        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .param("id", passengerId)
                .patch(BASE_PASSENGERS_URL);
    }

    @When("updates the passenger with id {string}")
    public void updatesThePassengerWithId(String id) {
        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .param("id", UUID.fromString(id))
                .patch(BASE_PASSENGERS_URL);
    }

    @When("retrieves the passenger by id")
    public void retrievesThePassengerById() {
        response = RestAssured.given()
                .param("id", passengerId)
                .get(BASE_PASSENGERS_URL);
    }

    @When("retrieves the passenger with id {string}")
    public void retrievesThePassengerWithId(String id) {
        response = RestAssured.given()
                .param("id", id)
                .get(BASE_PASSENGERS_URL);
    }

    @When("deletes the passenger by id")
    public void deletesThePassengerById() {
        response = RestAssured.given()
                .param("id", passengerId)
                .delete(BASE_PASSENGERS_URL);
    }

    @When("deletes the passenger with id {string}")
    public void deletesThePassengerWithId(String id) {
        response = RestAssured.given()
                .param("id", id)
                .delete(BASE_PASSENGERS_URL);
    }

    @Then("return response status {int}")
    public void returnResponseStatus(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @And("response body should contain")
    public void responseBodyShouёёldContain(String expectedResponse) {
        JsonPath actualResponse = response.jsonPath();
        JsonPath expectedJson = new JsonPath(expectedResponse);

        expectedJson.getMap("").forEach((key, value) -> {
            if (!key.equals("id")) {
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

    @And("the passenger should no longer exist")
    public void thePassengerShouldNoLongerExist() {
        Response getResponse = RestAssured.given()
                .param("id", passengerId)
                .get(BASE_PASSENGERS_URL);

        Assert.assertEquals(404, getResponse.getStatusCode());
    }

}

