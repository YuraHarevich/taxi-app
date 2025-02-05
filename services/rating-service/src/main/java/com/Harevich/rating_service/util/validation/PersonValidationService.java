package com.Harevich.rating_service.util.validation;

import java.util.UUID;

public interface PersonValidationService {
    void checkIfPersonExists(UUID id);
}
