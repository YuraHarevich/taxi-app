package com.Harevich.passengerservice.service;

import com.Harevich.passengerservice.dto.PassengerRequest;
import com.Harevich.passengerservice.dto.PassengerResponse;

import java.util.UUID;

public interface PassengerService {

    public PassengerResponse create(PassengerRequest request);

    public PassengerResponse update(PassengerRequest request, UUID id);

    public PassengerResponse getById(UUID id);

    public void deleteById(UUID passenger_id);

}
