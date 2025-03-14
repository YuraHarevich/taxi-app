Feature: Ride Service

  Scenario: Successfully create a new ride
    Given There is a ride service request
  """
    {
      "from": "Сталетава, 10",
      "to": "Таёжная 5",
      "passengerId": "id"
    }
  """
    When creates the ride
    Then return response status 201

  Scenario: Successfully retrieve an existing ride
    When retrieves the ride
    Then return response status 200
    And response body should contain
  """
    {
      "id": "4f118f57-eb21-4970-8042-e311ed4c3d2c",
      "from": "Сталетава, 10",
      "to": "Таёжная 5",
      "price": 500.00,
      "passengerId": "3f8ee7b1-e30b-4ce6-8661-1e2282e3dcb8",
      "driverId": "ea55537b-0f71-427c-96a9-4e5ef350ce28",
      "rideStatus": "CREATED",
      "rideTime": "12:30"
    }
  """

  Scenario: Fail to retrieve non-existing ride
    When retrieves the ride with id "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    Then return response status 404
    And response should contain error message
  """
    {
      "message": "Ride with such id not found"
    }
  """

  Scenario: Successfully create a new ride order
    Given There is a ride service request
  """
    {
      "from": "Сталетава, 10",
      "to": "Таёжная 5",
      "passengerId": "3f8ee7b1-e30b-4ce6-8661-1e2282e3dcb8"
    }
  """
    When creates the ride order
    Then return response status 200

  Scenario: Fail to create ride order with invalid request
    Given There is a ride service request
  """
    {
      "from": "",
      "to": "",
      "passengerId": "3f8ee7b1-e30b-4ce6-8661-1e2282e3dcb8"
    }
  """
    When creates the ride order
    Then return response status 400

  Scenario: Successfully create driver application
    When apply by driver
    Then return response status 200

  Scenario: Fail to create driver application
    When apply by driver with id "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    Then return response status 404

  Scenario: Successfully update an existing ride
    Given There is a ride service request
  """
    {
      "from": "ЦУМ",
      "to": "ГУМ",
      "passengerId": "3f8ee7b1-e30b-4ce6-8661-1e2282e3dcb8"
    }
  """
    When updates the ride
    Then return response status 202
    And response body should contain
  """
    {
      "id": "4f118f57-eb21-4970-8042-e311ed4c3d2c",
      "from": "ЦУМ",
      "to": "ГУМ",
      "price": 20.1,
      "passengerId": "3f8ee7b1-e30b-4ce6-8661-1e2282e3dcb8",
      "driverId": "ea55537b-0f71-427c-96a9-4e5ef350ce28",
      "rideStatus": "CREATED",
      "rideTime": "12:30"
    }
  """

  Scenario: Fail to update non-existing ride
    Given There is a ride service request
  """
    {
      "from": "New From Address",
      "to": "New To Address",
      "passengerId": "3f8ee7b1-e30b-4ce6-8661-1e2282e3dcb8"
    }
  """
    When updates the ride with id "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    Then return response status 404
    And response should contain error message
  """
    {
      "message": "Ride with such id not found"
    }
  """

  Scenario: Successfully change ride status
    When changes the ride status for ride
    Then return response status 202
    And response body should contain
  """
    {
      "id": "3f8ee7b1-e30b-4ce6-8661-1e2282e3dcb8",
      "rideStatus": "ACCEPTED"
    }
  """

  Scenario: Fail to change ride status for non-existing ride
    When changes the ride status for ride with id "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    Then return response status 404
    And response should contain error message
  """
    {
      "message": "Ride with such id not found"
    }
  """

  Scenario: Successfully retrieve all rides by passenger ID
    When retrieves all rides for passenger
    Then return response status 200

  Scenario: Successfully retrieve all rides by driver ID
    When retrieves all rides for driver
    Then return response status 200
