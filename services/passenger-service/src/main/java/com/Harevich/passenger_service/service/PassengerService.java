package com.Harevich.passenger_service.service;

import com.Harevich.passenger_service.dto.PassengerRequest;
import com.Harevich.passenger_service.dto.PassengerResponse;
import com.Harevich.passenger_service.exceptions.UniqueException;
import com.Harevich.passenger_service.mapper.PassengerMapper;
import com.Harevich.passenger_service.model.Passenger;
import com.Harevich.passenger_service.repository.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
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
                throw new UniqueException("email should be unique");
            } else if (rootMessage.contains("Key (number)")) {
                throw new UniqueException("phone number should be unique");
            }
        }
        return passenger;
    }

    public Passenger edit(PassengerRequest request, UUID id) {
        var passenger = passengerRepository.findById(id);
        if (passenger.isEmpty())
            throw new EntityNotFoundException("Passenger with such id not found");
        if (passenger.get().isDeleted() == true)
            throw new EntityNotFoundException("Passenger with such id is deleted");
        var changed_passenger = passenger.get();
        changed_passenger.setName(request.name());
        changed_passenger.setEmail(request.email());
        changed_passenger.setSurname(request.surname());
        changed_passenger.setNumber(request.number());
        return passengerRepository.saveAndFlush(changed_passenger);
    }

    public PassengerResponse getById(UUID id) {
        var optional = passengerRepository.findById(id);
        if (optional.isPresent()){
            if(optional.get().isDeleted() == true)
                throw new EntityNotFoundException("Passenger with such id is deleted");
            return new PassengerResponse(
                    optional.get().getId(),
                    optional.get().getName(),
                    optional.get().getSurname(),
                    optional.get().getEmail(),
                    optional.get().getNumber()
            );
        }
        else
            throw new EntityNotFoundException("Passenger with such id not found");

    }
    public void deleteById(UUID passenger_id){
        var optional = passengerRepository.findById(passenger_id);
        if (optional.isPresent()){
            optional.get().setDeleted(true);
            passengerRepository.save(optional.get());
        }
        else
            throw new EntityNotFoundException("Passenger with such id not found");
    }
}
