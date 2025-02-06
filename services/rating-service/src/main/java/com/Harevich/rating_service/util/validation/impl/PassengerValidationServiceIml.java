package com.Harevich.rating_service.util.validation.impl;

import com.Harevich.rating_service.util.validation.PersonValidationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PassengerValidationServiceIml implements PersonValidationService {

    @Override
    public void checkIfPersonExists(UUID id) {
        //todo: запрос в другой сервис
    }

}
