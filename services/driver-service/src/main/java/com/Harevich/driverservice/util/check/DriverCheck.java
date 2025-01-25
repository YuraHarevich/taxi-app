package com.Harevich.driverservice.util.check;

import java.util.UUID;

public interface DriverCheck {
    void alreadyExistsByEmail(String email);
    void alreadyExistsByNumber(String number);
    void existsById(UUID id);
    void isDeleted(UUID id);
}
