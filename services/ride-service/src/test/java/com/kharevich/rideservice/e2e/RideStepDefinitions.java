package com.kharevich.rideservice.e2e;

import com.kharevich.rideservice.model.enumerations.RideStatus;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.kharevich.rideservice.constants.RideFieldConstants.RIDE_FROM_FIELD;
import static com.kharevich.rideservice.constants.RideFieldConstants.RIDE_ID_FIELD;
import static com.kharevich.rideservice.constants.RideFieldConstants.RIDE_STATUS_FIELD;
import static com.kharevich.rideservice.constants.RideFieldConstants.RIDE_TOTAL_ELEMENTS_FIELD;
import static com.kharevich.rideservice.constants.RideFieldConstants.RIDE_TO_FIELD;
import static com.kharevich.rideservice.constants.TestConstants.DEFAULT_DRIVER_ID;
import static com.kharevich.rideservice.constants.TestConstants.DEFAULT_PASSENGER_ID;
import static com.kharevich.rideservice.constants.TestConstants.DEFAULT_RIDE_ID;
import static com.kharevich.rideservice.constants.TestConstants.RIDE_SERVICE_APPLY_FOR_DRIVER;
import static com.kharevich.rideservice.constants.TestConstants.RIDE_SERVICE_CHANGE_RIDE_STATUS;
import static com.kharevich.rideservice.constants.TestConstants.RIDE_SERVICE_CREATE_ORDER;
import static com.kharevich.rideservice.constants.TestConstants.RIDE_SERVICE_CREATE_RIDE;
import static com.kharevich.rideservice.constants.TestConstants.RIDE_SERVICE_GET_ALL_RIDES_DRIVER_ID;
import static com.kharevich.rideservice.constants.TestConstants.RIDE_SERVICE_GET_ALL_RIDES_PASSENGER_ID;
import static com.kharevich.rideservice.constants.TestConstants.RIDE_SERVICE_GET_RIDE_BY_ID;
import static com.kharevich.rideservice.constants.TestConstants.RIDE_SERVICE_HOST_NAME;
import static com.kharevich.rideservice.constants.TestConstants.RIDE_SERVICE_UPDATE_RIDE;
import static com.kharevich.rideservice.model.enumerations.RideStatus.ACCEPTED;
import static com.kharevich.rideservice.model.enumerations.RideStatus.CREATED;
import static com.kharevich.rideservice.model.enumerations.RideStatus.DECLINED;
import static org.hamcrest.Matchers.equalTo;

public class RideStepDefinitions {

    private String requestBody;
    private Response response;

    @Given("There is a ride service request")
    public void thereIsARideServiceRequest(String requestBody) throws JSONException {
        this.requestBody = requestBody;
    }

    @When("creates the ride")
    public void createsTheRide() throws JSONException {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(RIDE_SERVICE_HOST_NAME + RIDE_SERVICE_CREATE_RIDE + "?driver_id=" + DEFAULT_DRIVER_ID)
                .then()
                .extract()
                .response();
    }

    @When("creates the ride order")
    public void createsTheRideOrder() throws JSONException {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(RIDE_SERVICE_HOST_NAME + RIDE_SERVICE_CREATE_ORDER)
                .then()
                .extract()
                .response();
    }

    @When("apply by driver")
    public void applyByDriver() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .post(RIDE_SERVICE_HOST_NAME + RIDE_SERVICE_APPLY_FOR_DRIVER + "?driver_id=" + DEFAULT_DRIVER_ID)
                .then()
                .extract()
                .response();
    }

    @When("apply by driver with id {string}")
    public void applyByDriverWithId(String id) {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .post(RIDE_SERVICE_HOST_NAME + RIDE_SERVICE_APPLY_FOR_DRIVER + "?driver_id=" + id)
                .then()
                .extract()
                .response();
    }

    @Then("return response status {int}")
    public void returnResponseStatus(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @And("response body should contain")
    public void responseBodyShouldContain(String expectedResponse) {
        JsonPath actualResponse = response.jsonPath();
        JsonPath expectedJson = new JsonPath(expectedResponse);

        expectedJson.getMap("").forEach((key, value) -> {
            if (key.equals(RIDE_FROM_FIELD) || key.equals(RIDE_TO_FIELD)) {
                Assert.assertEquals(value, actualResponse.get(key.toString()));
            }
            if (key.equals(RIDE_STATUS_FIELD)) {
                Assert.assertTrue(value.equals(ACCEPTED.toString()) || value.equals(DECLINED.toString()) || value.equals(CREATED.toString()));
            }
        });
    }

    @When("updates the ride")
    public void updatesTheRideWithId() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .param(RIDE_ID_FIELD, DEFAULT_RIDE_ID)
                .patch(RIDE_SERVICE_HOST_NAME + RIDE_SERVICE_UPDATE_RIDE);
    }

    @When("updates the ride with id {string}")
    public void updatesTheRideWithNonExistingId(String id) {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .param(RIDE_ID_FIELD, id)
                .patch(RIDE_SERVICE_HOST_NAME + RIDE_SERVICE_UPDATE_RIDE);
    }

    @When("changes the ride status for ride")
    public void changesTheRideStatusForRide() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .patch(RIDE_SERVICE_HOST_NAME + RIDE_SERVICE_CHANGE_RIDE_STATUS + "?id=" + DEFAULT_RIDE_ID);
    }

    @When("changes the ride status for ride with id {string}")
    public void changesTheRideStatusForRideWithId(String id) {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .patch(RIDE_SERVICE_HOST_NAME + RIDE_SERVICE_CHANGE_RIDE_STATUS+"?id=" + id);
    }

    @When("retrieves the ride with id {string}")
    public void retrievesTheRideById(String id) {
        response = RestAssured.given()
                .get(RIDE_SERVICE_HOST_NAME + RIDE_SERVICE_GET_RIDE_BY_ID + "?id=" + id);
    }

    @When("retrieves the ride")
    public void retrievesTheRide() {
        response = RestAssured.given()
                .get(RIDE_SERVICE_HOST_NAME + RIDE_SERVICE_GET_RIDE_BY_ID + "?id=" + DEFAULT_RIDE_ID);
    }

    @When("retrieves all rides for passenger")
    public void retrievesAllRidesForPassenger() {
        response = RestAssured.given()
                .get(RIDE_SERVICE_HOST_NAME + RIDE_SERVICE_GET_ALL_RIDES_PASSENGER_ID + "?passenger_id=" + DEFAULT_PASSENGER_ID);
    }

    @When("retrieves all rides for driver")
    public void retrievesAllRidesForDriver() {
        response = RestAssured.given()
                .get(RIDE_SERVICE_HOST_NAME + RIDE_SERVICE_GET_ALL_RIDES_DRIVER_ID + "?driver_id=" + DEFAULT_DRIVER_ID);
    }

    @And("response body should contain a list of rides")
    public void responseBodyShouldContainAListOfRides() {
        response.then()
                .body(RIDE_TOTAL_ELEMENTS_FIELD, equalTo(1));
    }

    @And("response should contain error message")
    public void responseShouldContainErrorMessage(String expectedResponse) {
        JsonPath actualResponse = response.jsonPath();
        JsonPath expectedJson = new JsonPath(expectedResponse);

        expectedJson.getMap("").forEach((key, value) -> {
            Assert.assertEquals(value, actualResponse.get(key.toString()));
        });
    }

}

