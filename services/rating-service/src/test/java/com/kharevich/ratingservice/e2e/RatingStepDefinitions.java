package com.kharevich.ratingservice.e2e;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.kharevich.ratingservice.constants.TestConstants.RATING_SERVICE_ESTIMATE_RIDE;
import static com.kharevich.ratingservice.constants.TestConstants.RATING_SERVICE_GET_ALL_RATINGS_DRIVER;
import static com.kharevich.ratingservice.constants.TestConstants.RATING_SERVICE_GET_ALL_RATINGS_PASSENGER;
import static com.kharevich.ratingservice.constants.TestConstants.RATING_SERVICE_GET_RATING_DRIVER;
import static com.kharevich.ratingservice.constants.TestConstants.RATING_SERVICE_GET_RATING_PASSENGER;
import static com.kharevich.ratingservice.constants.TestConstants.RATING_SERVICE_HOST_NAME;
import static org.hamcrest.Matchers.equalTo;

@Transactional
public class RatingStepDefinitions {

    private Response response;
    private String requestBody;
    private static UUID passengerId;
    private static UUID driverId;
    private static UUID unFinishedRideId;
    private static UUID finishedRideId;

    @Given("assign passenger id {string} and driver id {string}")
    public void assignPassengerAndDriverId(String passenger_id,String driver_id) throws JSONException {
        passengerId = UUID.fromString(passenger_id);
        driverId = UUID.fromString(driver_id);
    }

    @Given("assign finished ride id {string} and unfinished ride id {string}")
    public void assignFinishedAndUnfinishedRideIds(String finId,String unFinId) throws JSONException {
        finishedRideId = UUID.fromString(finId);
        unFinishedRideId = UUID.fromString(unFinId);
    }

    @Given("a valid rating request")
    public void validRatingRequest(String requestBody) throws JSONException {
        JSONObject jsonData = new JSONObject(requestBody);
        jsonData.put("rideId", finishedRideId);
        String updatedData = jsonData.toString();
        this.requestBody = updatedData;
    }

    @Given("non-valid rating request")
    public void non_validRatingRequest(String requestBody) throws JSONException {
        JSONObject jsonData = new JSONObject(requestBody);
        jsonData.put("rideId", unFinishedRideId);
        String updatedData = jsonData.toString();
        this.requestBody = updatedData;
    }

    @When("the ride is estimated with the following data")
    public void theRideIsEstimatedWithTheFollowingData() {
        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(RATING_SERVICE_HOST_NAME + RATING_SERVICE_ESTIMATE_RIDE);
    }

    @When("request all ratings for driver")
    public void requestAllRatingsForDriver() {
        response = RestAssured.given()
                .contentType("application/json")
                .param("driverId", driverId)
                .get(RATING_SERVICE_HOST_NAME + RATING_SERVICE_GET_ALL_RATINGS_DRIVER);    }

    @When("request all ratings for passenger")
    public void requestAllRatingsForPassenger() {
        response = RestAssured.given()
                .contentType("application/json")
                .param("passengerId", passengerId)
                .get(RATING_SERVICE_HOST_NAME + RATING_SERVICE_GET_ALL_RATINGS_PASSENGER);    }

    @When("request total rating for passenger")
    public void requestTheTotalRatingForPassenger() {
        response = RestAssured.given()
                .contentType("application/json")
                .param("passengerId", passengerId)
                .get(RATING_SERVICE_HOST_NAME + RATING_SERVICE_GET_RATING_PASSENGER);
    }

    @When("request total rating for driver")
    public void requestTheTotalRatingForDriver() {
        response = RestAssured.given()
                .contentType("application/json")
                .param("driverId", driverId)
                .get(RATING_SERVICE_HOST_NAME + RATING_SERVICE_GET_RATING_DRIVER);
    }

    @Then("response status should be {int}")
    public void returnResponseStatus(Integer statusCode) {
        response.then().statusCode(statusCode);
    }

    @And("response body should contain")
    public void responseBodyShouldContain(String expectedResponse) {
        JsonPath actualResponse = response.jsonPath();
        JsonPath expectedJson = new JsonPath(expectedResponse);

        expectedJson.getMap("").forEach((key, value) -> {
            if (!key.equals("rideId")) {
                Assert.assertEquals(value, actualResponse.get(key.toString()));
            }
        });
    }

    @And("total raiting should be {string}")
    public void totalRaitingShouldBe(String totalRating) {
        JsonPath actualResponse = response.jsonPath();
        Assert.assertEquals(actualResponse.getString("totalRating"),totalRating);
    }

    @And("response body should contain a list")
    public void responseBodyShouldContainAListOfRides() {
        response.then()
                .body("totalElements", equalTo(0));
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

