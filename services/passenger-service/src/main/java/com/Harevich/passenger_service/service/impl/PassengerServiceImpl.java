package com.Harevich.passenger_service.service.impl;

import com.Harevich.passenger_service.dto.PassengerRequest;
import com.Harevich.passenger_service.dto.PassengerResponse;
import com.Harevich.passenger_service.exceptions.UniqueException;
import com.Harevich.passenger_service.service.PassengerService;
import com.Harevich.passenger_service.util.constants.PassengerServiceResponseConstants;
import com.Harevich.passenger_service.util.mapper.PassengerMapper;
import com.Harevich.passenger_service.model.Passenger;
import com.Harevich.passenger_service.repository.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerMapper passengerMapper;
    private final PassengerRepository passengerRepository;

    public PassengerResponse registrate(PassengerRequest request) {
        var passenger = passengerMapper.toPassenger(request);
        try {
            passengerRepository.saveAndFlush(passenger);
        } catch (DataIntegrityViolationException ex) {
            String rootMessage = ex.getMessage(); // Сообщение базы данных
            if (rootMessage.contains("Key (email)")) {
                throw new UniqueException(PassengerServiceResponseConstants.REPEATED_EMAIL);
            } else if (rootMessage.contains("Key (number)")) {
                throw new UniqueException(PassengerServiceResponseConstants.REPEATED_PHONE_NUMBER);
            }
        }
        return passengerMapper.toResponse(passenger);
    }

    public PassengerResponse edit(PassengerRequest request, UUID id) {
        var passenger = passengerRepository.findById(id);
        if (passenger.isEmpty())
            throw new EntityNotFoundException(PassengerServiceResponseConstants.PASSENGER_NOT_FOUND);
        if (passenger.get().isDeleted() == true)
            throw new EntityNotFoundException(PassengerServiceResponseConstants.PASSENGER_DELETED);
        Passenger changingPassenger = passenger.get();
        passengerMapper.changePassengerByRequest(request,changingPassenger);
        Passenger temp = passengerRepository.saveAndFlush(changingPassenger);
        return passengerMapper.toResponse(temp);
    }

    public PassengerResponse getById(UUID id) {
        var optional = passengerRepository.findById(id);
        if (optional.isPresent()){
            if(optional.get().isDeleted() == true)
                throw new EntityNotFoundException(PassengerServiceResponseConstants.PASSENGER_DELETED);
            return passengerMapper.toResponse(optional.get());
        }
        else
            throw new EntityNotFoundException(PassengerServiceResponseConstants.PASSENGER_NOT_FOUND);

    }
    public void deleteById(UUID passenger_id){
        var optional = passengerRepository.findById(passenger_id);
        if (optional.isPresent()){
            if(optional.get().isDeleted()==true)
                throw new EntityNotFoundException(PassengerServiceResponseConstants.PASSENGER_DELETED);
            optional.get().setDeleted(true);
            passengerRepository.save(optional.get());
        }
        else
            throw new EntityNotFoundException(PassengerServiceResponseConstants.PASSENGER_NOT_FOUND);
    }
}
