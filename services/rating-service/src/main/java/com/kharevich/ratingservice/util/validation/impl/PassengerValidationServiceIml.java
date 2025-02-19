package com.kharevich.ratingservice.util.validation.impl;

import com.kharevich.ratingservice.util.validation.PersonValidationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PassengerValidationServiceIml implements PersonValidationService {

    @Override
    public void checkIfPersonExists(UUID id) {
        //todo: запрос в другой сервис
    }

}
