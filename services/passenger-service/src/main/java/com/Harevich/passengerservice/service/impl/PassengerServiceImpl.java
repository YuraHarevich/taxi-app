package com.Harevich.passengerservice.service.impl;

import com.Harevich.passengerservice.dto.PassengerRequest;
import com.Harevich.passengerservice.dto.PassengerResponse;
import com.Harevich.passengerservice.service.PassengerService;
import com.Harevich.passengerservice.util.check.PassengerCheck;
import com.Harevich.passengerservice.util.constants.PassengerServiceResponseConstants;
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
    private final PassengerCheck check;

    @Transactional
    public PassengerResponse registrate(PassengerRequest request) {
        check.alreadyExistsByEmail(request.email());
        check.alreadyExistsByNumber(request.number());
        Passenger passenger = passengerMapper.toPassenger(request);

        return passengerMapper
                .toResponse(passengerRepository
                        .save(passengerMapper
                                .toPassenger(request)));
    }

    @Transactional
    public PassengerResponse edit(PassengerRequest request, UUID id) {
        check.existsById(id);
        Passenger passenger = passengerRepository.findById(id).get();
        check.isDeleted(id);
        check.alreadyExistsByEmail(request.email());
        check.alreadyExistsByNumber(request.number());
        passengerMapper.changePassengerByRequest(request,passenger);
        Passenger updatedPassenger = passengerRepository.saveAndFlush(passenger);
        return passengerMapper.toResponse(updatedPassenger);
    }

    public PassengerResponse getById(UUID id) {
        var passenger = passengerRepository
                .findById(id)
                .orElseThrow(()-> new EntityNotFoundException(PassengerServiceResponseConstants.PASSENGER_NOT_FOUND));
        if (passenger.isDeleted())
            throw new EntityNotFoundException(PassengerServiceResponseConstants.PASSENGER_DELETED);
        return passengerMapper.toResponse(passenger);
    }
    @Transactional
    public void deleteById(UUID passenger_id){
        check.existsById(passenger_id);
        check.isDeleted(passenger_id);
        Passenger passenger = passengerRepository.findById(passenger_id).get();
        passenger.setDeleted(true);
        passengerRepository.save(passenger);
    }
}
