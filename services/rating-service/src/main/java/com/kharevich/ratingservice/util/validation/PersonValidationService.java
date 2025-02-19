package com.kharevich.ratingservice.util.validation;

import java.util.UUID;

public interface PersonValidationService {

    void checkIfPersonExists(UUID id);

}
