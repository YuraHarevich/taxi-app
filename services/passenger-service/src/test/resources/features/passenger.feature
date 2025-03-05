Feature: Passenger Service
Feature: Passenger Management API

  # Позитивный сценарий: создание пассажира
  Scenario: Successfully create a new passenger
    Given There is passenger service request
    """
      {
        "name": "Yura",
        "surname": "Harevich",
        "email": "mymail@gmail.com",
        "number": "+375447525709"
      }
    """
    When creates the passenger
    Then return response status 201
    And response body should contain
    """
      {
        "name": "Yura",
        "surname": "Harevich",
        "email": "mymail@gmail.com",
        "number": "+375447525709"
      }
    """

  # Негативный сценарий: создание пассажира с повторяющимся email
  Scenario: Fail to create passenger with repeated email
    Given There is passenger service request
    """
      {
        "name": "Yura",
        "surname": "Harevich",
        "email": "mymail@gmail.com",
        "number": "+375447525708"
      }
    """
    When creates the passenger
    Then return response status 409
    And response should contain error message
    """
    {
      "message": "Passenger with such email already exists"
    }
    """

  # Негативный сценарий: создание пассажира с повторяющимся номером телефона
  Scenario: Fail to create passenger with repeated phone number
    Given There is passenger service request
    """
      {
        "name": "Yura",
        "surname": "Harevich",
        "email": "mymail1@gmail.com",
        "number": "+375447525709"
      }
    """
    When creates the passenger
    Then return response status 409
    And response should contain error message
    """
    {
      "message": "Passenger with such phone number already exists"
    }
    """

  # Позитивный сценарий: обновление пассажира
  Scenario: Successfully update an existing passenger
    Given There is passenger service request
    """
      {
        "name": "Alex",
        "surname": "Messi",
        "email": "update@example.com",
        "number": "+375447777777"
      }
    """
    When updates the passenger with id
    Then return response status 202
    And response body should contain
    """
      {
        "name": "Alex",
        "surname": "Messi",
        "email": "update@example.com",
        "number": "+375447777777"
      }
    """

  # Негативный сценарий: обновление несуществующего пассажира
  Scenario: Fail to update non-existing passenger
    Given There is passenger service request
    """
      {
        "name": "NonExisting",
        "surname": "User",
        "email": "nonexist@example.com",
        "number": "+375335555555"
      }
    """
    When updates the passenger with id "beab9ed9-ea27-461b-a87d-62890408b154"
    Then return response status 404
    And response should contain error message
    """
    {
      "message": "Passenger with such id not found"
    }
    """

  # Позитивный сценарий: получение информации о пассажире
  Scenario: Successfully retrieve an existing passenger
    When retrieves the passenger by id
    Then return response status 200
    And response body should contain
    """
      {
        "name": "Alex",
        "surname": "Messi",
        "email": "update@example.com",
        "number": "+375447777777"
      }
    """

  # Негативный сценарий: получение информации о несуществующем пассажире
  Scenario: Fail to retrieve non-existing passenger
    When retrieves the passenger with id "beab9ed9-ea27-461b-a87d-62890408b154"
    Then return response status 404
    And response should contain error message
    """
    {
      "message": "Passenger with such id not found"
    }
    """

  # Позитивный сценарий: удаление пассажира
  Scenario: Successfully delete an existing passenger
    When deletes the passenger by id
    Then return response status 200
    And the passenger should no longer exist

  # Негативный сценарий: удаление несуществующего пассажира
  Scenario: Fail to delete non-existing passenger
    When deletes the passenger with id "beab9ed9-ea27-461b-a87d-62890408b154"
    Then return response status 404
    And response should contain error message
    """
    {
      "message": "Passenger with such id not found"
    }
    """
