package com.kharevich.rideservice.e2e;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import static com.kharevich.rideservice.constants.TestConstants.RIDE_SERVICE_APPLY_FOR_DRIVER;
import static com.kharevich.rideservice.constants.TestConstants.RIDE_SERVICE_CREATE_ORDER;
import static com.kharevich.rideservice.constants.TestConstants.RIDE_SERVICE_CREATE_RIDE;
import static com.kharevich.rideservice.constants.TestConstants.RIDE_SERVICE_HOST_NAME;
import static org.hamcrest.Matchers.equalTo;

@Transactional
public class RideStepDefinitions {

    private String requestBody;
    private Response response;
    private String rideId;

    @Given("There is a ride service request")
    public void thereIsARideServiceRequest(String requestBody) {
        this.requestBody = requestBody;
    }

    @When("creates the ride order")
    public void createsTheRideOrder() {
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(RIDE_SERVICE_HOST_NAME + RIDE_SERVICE_CREATE_ORDER)
                .then()
                .extract()
                .response();
        this.response = response;
    }

    @When("apply by driver with id {string}")
    public void applyByDriver(String driverId) {
        Response response = RestAssured.given()
                .contentType("application/json")
                .param("driver_id",driverId)
                .post(RIDE_SERVICE_HOST_NAME + RIDE_SERVICE_APPLY_FOR_DRIVER)
                .then()
                .extract()
                .response();
        this.response = response;
    }

    @Then("return response status {int}")
    public void returnResponseStatus(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @And("response body should contain")
    public void responseBodyShouldContain(String expectedResponse) {
        response.then().body(equalTo(expectedResponse));
    }

    @And("response should contain error message")
    public void responseShouldContainErrorMessage(String expectedErrorMessage) {
        response.then().body("message", equalTo(expectedErrorMessage));
    }

    @When("updates the ride with id {string}")
    public void updatesTheRideWithId(String id) {
//        response = RestAssured.given()
//                .contentType("application/json")
//                .body(requestBody)
//                .param("id", passengerId)
//                .patch(BASE_PASSENGERS_URL);
    }

    @When("changes the ride status for ride with id {string}")
    public void changesTheRideStatusForRideWithId(String id) {
        rideId = id;
        //response = request.when().patch("/api/v1/rides/change-status?id=" + rideId);
    }

    @When("retrieves the ride by id {string}")
    public void retrievesTheRideById(String id) {
        rideId = id;
        //response = request.when().get("/api/v1/rides?id=" + rideId);
    }

    @When("retrieves all rides for passenger with id {string}")
    public void retrievesAllRidesForPassengerWithId(String passengerId) {
        //response = request.when().get("/api/v1/rides/all/passenger?passenger_id=" + passengerId);
    }

    @And("response body should contain a list of rides")
    public void responseBodyShouldContainAListOfRides() {
        //response.then().body("content", equalTo(List.of()));
    }

}

