package com.Harevich.passengerservice.util.check;

import java.util.UUID;

public interface PassengerCheck {
    void alreadyExistsByEmail(String email);
    void alreadyExistsByNumber(String number);
    void existsById(UUID id);
    void isDeleted(UUID id);
}
