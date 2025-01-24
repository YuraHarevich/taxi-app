package com.Harevich.passenger_service.service;

import com.Harevich.passenger_service.dto.PassengerRequest;
import com.Harevich.passenger_service.dto.PassengerResponse;
import com.Harevich.passenger_service.exceptions.UniqueException;
import com.Harevich.passenger_service.model.Passenger;
import com.Harevich.passenger_service.util.constants.PassengerServiceResponseConstants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.UUID;

public interface PassengerService {
    public PassengerResponse registrate(PassengerRequest request);

    public PassengerResponse edit(PassengerRequest request, UUID id);

    public PassengerResponse getById(UUID id);

    public void deleteById(UUID passenger_id);
}
