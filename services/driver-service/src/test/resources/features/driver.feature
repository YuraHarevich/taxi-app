Feature: driver Service
  ############################## Сценарии с водителями  ##############################

  # Позитивный сценарий: создание водителя
  Scenario: Successfully create a new driver
    Given There is driver service request
    """
      {
        "name": "Maksim",
        "surname": "Komissarov",
        "email": "jury.harevich@gmail.com",
        "number": "+375447525701",
        "sex": "MALE"
      }
    """
    When creates the driver
    Then return driver response status 201
    And driver response body should contain
    """
      {
        "id": "401a7641-f3c7-4d71-b2a1-d18851ad44bc",
        "name": "Maksim",
        "surname": "Komissarov",
        "email": "jury.harevich@gmail.com",
        "sex": "MALE",
        "carId": null
      }
    """

  # Негативный сценарий: создание водителя с повторяющимся email
  Scenario: Fail to create driver with repeated email
    Given There is driver service request
    """
      {
        "name": "Maksim",
        "surname": "Komissarov",
        "email": "jury.harevich@gmail.com",
        "number": "+375447525702",
        "sex": "MALE"
      }
    """
    When creates the driver
    Then return driver response status 409
    And driver response should contain error message
    """
    {
      "message": "Driver with such email already exists"
    }
    """

  # Негативный сценарий: создание водителя с повторяющимся номером телефона
  Scenario: Fail to create driver with repeated phone number
    Given There is driver service request
    """
      {
        "name": "Maksim",
        "surname": "Komissarov",
        "email": "mymail1@gmail.com",
        "number": "+375447525701",
        "sex": "MALE"
      }
    """
    When creates the driver
    Then return driver response status 409
    And driver response should contain error message
    """
    {
      "message": "Driver with such phone number already exists"
    }
    """

  # Позитивный сценарий: обновление водителя
  Scenario: Successfully update an existing driver
    Given There is driver service request
    """
      {
        "name": "Alex",
        "surname": "Messi",
        "email": "update@example.com",
        "number": "+375447777777",
        "sex": "MALE"
      }
    """
    When updates the driver with id
    Then return driver response status 202
    And driver response body should contain
    """
      {
        "id": "401a7641-f3c7-4d71-b2a1-d18851ad44bc",
        "name": "Alex",
        "surname": "Messi",
        "email": "update@example.com",
        "sex": "MALE",
        "carId": null
      }
    """

  # Негативный сценарий: обновление несуществующего водителя
  Scenario: Fail to update non-existing driver
    Given There is driver service request
    """
      {
        "name": "NonExisting",
        "surname": "User",
        "email": "nonexist@example.com",
        "number": "+375335555555",
        "sex": "MALE"
      }
    """
    When updates the driver with id "beab9ed9-ea27-461b-a87d-62890408b154"
    Then return driver response status 404
    And driver response should contain error message
    """
    {
      "message": "Driver not found"
    }
    """

  # Позитивный сценарий: получение информации о водителе
  Scenario: Successfully retrieve an existing driver
    When retrieves the driver by id
    Then return driver response status 200
    And driver response body should contain
    """
      {
        "id": "401a7641-f3c7-4d71-b2a1-d18851ad44bc",
        "name": "Alex",
        "surname": "Messi",
        "email": "update@example.com",
        "sex": "MALE",
        "carId": null
      }
    """

  # Негативный сценарий: получение информации о несуществующем водителе
  Scenario: Fail to retrieve non-existing driver
    When retrieves the driver with id "beab9ed9-ea27-461b-a87d-62890408b154"
    Then return driver response status 404
    And driver response should contain error message
    """
    {
      "message": "Driver not found"
    }
    """

  # Позитивный сценарий: удаление водителя
  Scenario: Successfully delete an existing driver
    When deletes the driver by id
    Then return driver response status 200
    And the driver should no longer exist

  # Негативный сценарий: удаление несуществующего водителя
  Scenario: Fail to delete non-existing driver
    When deletes the driver with id "beab9ed9-ea27-461b-a87d-62890408b154"
    Then return driver response status 404
