package com.Harevich.passengerservice.service.impl;

import com.Harevich.passengerservice.dto.PassengerRequest;
import com.Harevich.passengerservice.dto.PassengerResponse;
import com.Harevich.passengerservice.service.PassengerService;
import com.Harevich.passengerservice.util.check.PassengerValidation;
import com.Harevich.passengerservice.util.constants.PassengerValidationConstants;
import com.Harevich.passengerservice.util.mapper.PassengerMapper;
import com.Harevich.passengerservice.model.Passenger;
import com.Harevich.passengerservice.repository.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerMapper passengerMapper;

    private final PassengerRepository passengerRepository;

    private final PassengerValidation passengerValidation;

    @Transactional
    public PassengerResponse create(PassengerRequest request) {
        passengerValidation.alreadyExistsByEmail(request.email());
        passengerValidation.alreadyExistsByNumber(request.number());
        Passenger passenger = passengerRepository.save(passengerMapper.toPassenger(request));
        return passengerMapper
                .toResponse(passenger);
    }

    @Transactional
    public PassengerResponse update(PassengerRequest request, UUID id) {
        passengerValidation.existsById(id);

        Passenger passenger = passengerRepository.findById(id).get();

        passengerValidation.isDeleted(id);
        passengerValidation.alreadyExistsByEmail(request.email());
        passengerValidation.alreadyExistsByNumber(request.number());

        passengerMapper.changePassengerByRequest(request,passenger);

        Passenger updatedPassenger = passengerRepository.saveAndFlush(passenger);
        return passengerMapper.toResponse(updatedPassenger);
    }

    public PassengerResponse getById(UUID id) {
        var passenger = passengerRepository
                .findById(id)
                .orElseThrow(()-> new EntityNotFoundException(PassengerValidationConstants.PASSENGER_NOT_FOUND));
        passengerValidation.isDeleted(id);
        return passengerMapper.toResponse(passenger);
    }
    @Transactional
    public void deleteById(UUID passenger_id){
        passengerValidation.existsById(passenger_id);
        passengerValidation.isDeleted(passenger_id);

        Passenger passenger = passengerRepository.findById(passenger_id).get();
        passenger.setDeleted(true);
        passengerRepository.save(passenger);
    }

}
