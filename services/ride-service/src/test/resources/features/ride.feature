Feature: Ride Service

  Scenario: assign ids
    Given assign passenger id "8bbba50d-df84-45d7-b928-9643f8e2284e" and driver id "2ad438ae-95e1-4e5e-b8e2-8b0f726ab6e2"

  Scenario: Successfully create a new ride
    Given There is a ride service request
  """
    {
      "from": "Мендзялеева 13",
      "to": "Таёжная 49",
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
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "from": "Мендзялеева 13",
      "to": "Таёжная 49",
      "price": 20.1,
      "passengerId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "driverId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "rideStatus": "ACCEPTED",
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
      "from": "Мендзялеева 13",
      "to": "Таёжная 49",
      "passengerId": "id"
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
      "passengerId": "id"
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
      "passengerId": "id"
    }
  """
    When updates the ride
    Then return response status 202
    And response body should contain
  """
    {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "from": "ЦУМ",
      "to": "ГУМ",
      "price": 20.1,
      "passengerId": "id",
      "driverId": "id",
      "rideStatus": "ACCEPTED",
      "rideTime": "12:30"
    }
  """

  Scenario: Fail to update non-existing ride
    Given There is a ride service request
  """
    {
      "from": "New From Address",
      "to": "New To Address",
      "passengerId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
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
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
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
