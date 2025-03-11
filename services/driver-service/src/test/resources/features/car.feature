Feature: car Service

  # Позитивный сценарий: создание автомобиля
  Scenario: Successfully create a new car
    Given There is car service request
    """
      {
        "color": "red",
        "number": "7777 BB-7",
        "brand": "BMW"
      }
    """
    When creates the car
    Then return car response status 201
    And car response body should contain
    """
      {
        "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "color": "red",
        "number": "7777 BB-7",
        "brand": "BMW",
        "driverId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
      }
    """

  # Негативный сценарий: создание автомобиля с повторяющимся номером
  Scenario: Fail to create car with repeated number
    Given There is car service request
    """
      {
        "color": "blue",
        "number": "7777 BB-7",
        "brand": "Audi"
      }
    """
    When creates the car
    Then return car response status 409

 # Позитивный сценарий: получение информации о автомобиле
  Scenario: Successfully retrieve an existing car
    When retrieves the car by id
    Then return car response status 200
    And car response body should contain
    """
      {
        "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "color": "red",
        "number": "7777 BB-7",
        "brand": "BMW",
        "driverId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
      }
    """

      # Негативный сценарий: обновление несуществующего автомобиля
  Scenario: Fail to update non-existing car
    Given There is car service request
    """
      {
        "color": "green",
        "number": "7777 BB-1",
        "brand": "Tesla"
      }
    """
    When updates the car with id "beab9ed9-ea27-461b-a87d-62890408b154"
    Then return car response status 409
    And car response should contain error message
    """
    {
      "message": "Car not found"
    }
    """

  # Позитивный сценарий: обновление автомобиля
  Scenario: Successfully update an existing car
    Given There is car service request
    """
      {
        "color": "blue",
        "number": "1777 BB-7",
        "brand": "Audi"
      }
    """
    When updates the car with id
    Then return car response status 202
    And car response body should contain
    """
      {
        "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "color": "blue",
        "number": "1777 BB-7",
        "brand": "Audi",
        "driverId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
      }
    """

  # Негативный сценарий: получение информации о несуществующем автомобиле
  Scenario: Fail to retrieve non-existing car
    When retrieves the car with id "beab9ed9-ea27-461b-a87d-62890408b154"
    Then return car response status 409
    And car response should contain error message
    """
    {
      "message": "Car not found"
    }
    """

  # Позитивный сценарий: удаление автомобиля
  Scenario: Successfully delete an existing car
    When deletes the car by id
    Then return car response status 200
    And the car should no longer exist

  # Негативный сценарий: удаление несуществующего автомобиля
  Scenario: Fail to delete non-existing car
    When deletes the car with id "beab9ed9-ea27-461b-a87d-62890408b154"
    Then return car response status 409
    And car response should contain error message
    """
    {
      "message": "Car not found"
    }
    """
