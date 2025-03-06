Feature: Ride Service

  Scenario: Successfully create a new ride order
    Given There is a ride service request
  """
    {
      "from": "Мендзялеева 13",
      "to": "Таёжная 49",
      "passengerId": "dd3c9688-b921-4c69-9530-f2e0343dce78"
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
      "passengerId": "dd3c9688-b921-4c69-9530-f2e0343dce78"
    }
  """
    When creates the ride order
    Then return response status 400

  Scenario: Successfully create driver application
    When apply by driver with id "552848b1-cf59-4990-a95d-28f69a472ca6"
    Then return response status 200

  Scenario: Fail to create driver application
    Given There is a ride service request
  """
    {
      "from": "",
      "to": "",
      "passengerId": "dd3c9688-b921-4c69-9530-f2e0343dce78"
    }
  """
    When creates the ride order
    Then return response status 400

    #!!!!!!!!!!!!!!!!!!!!!updates the ride with id "3fa85f64-5717-4562-b3fc-2c963f66afa6"
  Scenario: Successfully update an existing ride
    Given There is a ride service request
  """
    {
      "from": "ЦУМ",
      "to": "ГУМ",
      "passengerId": "dd3c9688-b921-4c69-9530-f2e0343dce78"
    }
  """
    When updates the ride with id "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    Then return response status 202
    And response body should contain
  """
    {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "from": "New From Address",
      "to": "New To Address",
      "price": 20.1,
      "passengerId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "driverId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
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
    When updates the ride with id "non-existing-id"
    Then return response status 404
    And response should contain error message
  """
    {
      "message": "Ride with such id not found"
    }
  """
 #!!!!!!!!!!!rideStatus
  Scenario: Successfully change ride status
    When changes the ride status for ride with id "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    Then return response status 202
    And response body should contain
  """
    {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "rideStatus": "ACCEPTED"
    }
  """

  Scenario: Fail to change status for non-existing ride
    When changes the ride status for ride with id "non-existing-id"
    Then return response status 404
    And response should contain error message
  """
    {
      "message": "Ride with such id not found"
    }
  """

  Scenario: Successfully retrieve an existing ride
    When retrieves the ride by id "3fa85f64-5717-4562-b3fc-2c963f66afa6"
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
    When retrieves the ride with id "non-existing-id"
    Then return response status 404
    And response should contain error message
  """
    {
      "message": "Ride with such id not found"
    }
  """

  Scenario: Successfully retrieve all rides by passenger ID
    When retrieves all rides for passenger with id "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    Then return response status 200
    And response body should contain a list of rides

  Scenario: Fail to retrieve rides for non-existing passenger
    When retrieves all rides for passenger with id "non-existing-id"
    Then return response status 404
    And response should contain error message
  """
    {
      "message": "Passenger with such id not found"
    }
  """