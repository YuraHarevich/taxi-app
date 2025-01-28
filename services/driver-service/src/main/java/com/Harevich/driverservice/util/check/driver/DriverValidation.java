package com.Harevich.driverservice.util.check.driver;

import java.util.UUID;

public interface DriverValidation {
    void alreadyExistsByEmail(String email);
    void alreadyExistsByNumber(String number);
    void existsById(UUID id);
    void isDeleted(UUID id);
}
