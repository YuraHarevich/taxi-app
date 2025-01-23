package com.Harevich.passenger_service.service;

import com.Harevich.passenger_service.dto.PassengerRequest;
import com.Harevich.passenger_service.dto.PassengerResponse;
import com.Harevich.passenger_service.exceptions.UniqueException;
import com.Harevich.passenger_service.util.constants.PassengerServiceResponseConstants;
import com.Harevich.passenger_service.util.mapper.PassengerMapper;
import com.Harevich.passenger_service.model.Passenger;
import com.Harevich.passenger_service.repository.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PassengerService {
    private final PassengerRepository passengerRepository;

    public Passenger registrate(PassengerRequest request) {
        var passenger = PassengerMapper.toPassenger(request);
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
        return passenger;
    }

    public Passenger edit(PassengerRequest request, UUID id) {
        var passenger = passengerRepository.findById(id);
        if (passenger.isEmpty())
            throw new EntityNotFoundException(PassengerServiceResponseConstants.PASSENGER_NOT_FOUND);
        if (passenger.get().isDeleted() == true)
            throw new EntityNotFoundException(PassengerServiceResponseConstants.PASSENGER_DELETED);
        var changed_passenger = passenger.get();
        changed_passenger.setName(request.name());
        changed_passenger.setEmail(request.email());
        changed_passenger.setSurname(request.surname());
        changed_passenger.setNumber(request.number());
        return passengerRepository.saveAndFlush(changed_passenger);
    }

    public Passenger getById(UUID id) {
        var optional = passengerRepository.findById(id);
        if (optional.isPresent()){
            if(optional.get().isDeleted() == true)
                throw new EntityNotFoundException(PassengerServiceResponseConstants.PASSENGER_DELETED);
            return optional.get();
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
