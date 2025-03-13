Feature: Rating Controller

  Scenario: assign ids
    Given assign passenger id "8bbba50d-df84-45d7-b928-9643f8e2284e" and driver id "cde48a21-efa8-4272-947c-7e45ab7ad46c"

  Scenario: assign ids
    Given assign finished ride id "f8b9bfe2-48d8-4149-acf4-6c992156f9c8" and unfinished ride id "f8b9bfe2-48d8-4149-acf4-6c992156f9c8"

  Scenario: Successfully estimate the ride
    Given a valid rating request
    """
      {
        "rideId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "rating": 3,
        "whoIsRated": "PASSENGER",
        "feedback": "в целом норм"
      }
    """
    When the ride is estimated with the following data
    Then response status should be 201
    And response body should contain
      """
      {
        "rideId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "whoIsRated": "passenger",
        "rating": 3,
        "feedback": "в целом норм"
      }
      """

  Scenario: Successfully estimate the ride by another person
    Given a valid rating request
    """
      {
        "rideId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "rating": 3,
        "whoIsRated": "DRIVER",
        "feedback": "в целом норм"
      }
    """
    When the ride is estimated with the following data
    Then response status should be 201
    And response body should contain
      """
      {
        "rideId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "whoIsRated": "driver",
        "rating": 3,
        "feedback": "в целом норм"
      }
      """

  Scenario: Fail to estimate unfinished
    Given non-valid rating request
    """
      {
        "rideId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "rating": 3,
        "whoIsRated": "DRIVER",
        "feedback": "в целом норм"
      }
    """
    When the ride is estimated with the following data
    Then response status should be 409
    And response should contain error message
        """
      {
         "message": "Ride is not finished yet"
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
