Feature: Rating Controller

  Scenario: assign ids
    Given assign passenger id "8bbba50d-df84-45d7-b928-9643f8e2284e" and driver id "2ad438ae-95e1-4e5e-b8e2-8b0f726ab6e2"

  Scenario: assign ids
    Given "assign finished ride id a7955f40-c68a-4cf7-b18b-b44eac51bd7d and unfinished ride id 0d8d8d12-bd31-48a8-93ed-789b9d71beb2

  Scenario: Successfully estimate the ride
    Given a valid rating request
    When the ride is estimated with the following data:
      """
      {
        "rideId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "rating": 5,
        "whoIsRated": "PASSENGER",
        "feedback": "в целом норм"
      }
      """
    Then response status should be 201
    And the response body should contain:
      """
      {
        "rideId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "whoIsRated": "PASSENGER",
        "rating": 5,
        "feedback": "в целом норм"
      }
      """

  Scenario: Fail to estimate unfinished
    Given an invalid rating request
    When the ride is estimated with the following data:
      """
      {
        "rideId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "rating": 6,
        "whoIsRated": "PASSENGER",
        "feedback": "в целом норм"
      }
      """
    Then the response status should be 400
    And the response body should contain:
      """
      {
        "message": "Rating must be between 1 and 5"
      }
      """

  Scenario: Get all ratings by driver ID
    When request all ratings for driver
    Then response status should be 200
    And response body should contain a list

  Scenario: Get all ratings by passenger
    When request all ratings for passenger
    Then response status should be 200
    And response body should contain a list

  Scenario: Get passenger rating
    When request total rating for passenger
    Then response status should be 200
    And total raiting should be "5.0"

  Scenario: Get driver rating
    When request total rating for driver
    Then response status should be 200
    And total raiting should be "5.0"
