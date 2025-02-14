package com.Harevich.ratingservice.util.validation.impl;

import com.Harevich.ratingservice.util.validation.PersonValidationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DriverValidationServiceIml implements PersonValidationService {

    @Override
    public void checkIfPersonExists(UUID id) {
        //todo: запрос в другой сервис
    }

}
