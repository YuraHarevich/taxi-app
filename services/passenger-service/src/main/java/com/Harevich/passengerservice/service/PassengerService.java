package com.Harevich.passengerservice.service;

import com.Harevich.passengerservice.dto.PassengerRequest;
import com.Harevich.passengerservice.dto.PassengerResponse;

import java.util.UUID;

public interface PassengerService {
    public PassengerResponse registrate(PassengerRequest request);

    public PassengerResponse edit(PassengerRequest request, UUID id);

    public PassengerResponse getById(UUID id);

    public void deleteById(UUID passenger_id);
}
