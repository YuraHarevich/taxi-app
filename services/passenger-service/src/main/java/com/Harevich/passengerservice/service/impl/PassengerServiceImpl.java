package com.Harevich.passengerservice.service.impl;

import com.Harevich.passengerservice.dto.PassengerRequest;
import com.Harevich.passengerservice.dto.PassengerResponse;
import com.Harevich.passengerservice.exceptions.UniqueException;
import com.Harevich.passengerservice.service.PassengerService;
import com.Harevich.passengerservice.util.additional.PassengerValidation;
import com.Harevich.passengerservice.util.constants.PassengerServiceResponseConstants;
import com.Harevich.passengerservice.util.mapper.PassengerMapper;
import com.Harevich.passengerservice.model.Passenger;
import com.Harevich.passengerservice.repository.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerMapper passengerMapper;
    private final PassengerRepository passengerRepository;
    private final PassengerValidation passengerValidation;

    @Transactional
    public PassengerResponse registrate(PassengerRequest request) {
        Passenger passenger = passengerMapper.toPassenger(request);
        try {
            passengerRepository.saveAndFlush(passenger);
        } catch (DataIntegrityViolationException ex) {
            String rootMessage = ex.getMessage();
            passengerValidation.handleUniqueExceptionByErrorMessage(rootMessage);
        }
        return passengerMapper.toResponse(passenger);
    }

    @Transactional
    public PassengerResponse edit(PassengerRequest request, UUID id) {
        var passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PassengerServiceResponseConstants.PASSENGER_NOT_FOUND));
        if (passenger.isDeleted() == true)
            throw new EntityNotFoundException(PassengerServiceResponseConstants.PASSENGER_DELETED);
        passengerMapper.changePassengerByRequest(request,passenger);
        Passenger updatedPassenger = passengerRepository.saveAndFlush(passenger);
        return passengerMapper.toResponse(updatedPassenger);
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
    @Transactional
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
