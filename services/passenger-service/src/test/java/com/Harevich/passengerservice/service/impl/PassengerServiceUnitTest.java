package com.Harevich.passengerservice.service.impl;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.Harevich.passengerservice.dto.PassengerRequest;
import com.Harevich.passengerservice.dto.PassengerResponse;
import com.Harevich.passengerservice.model.Passenger;
import com.Harevich.passengerservice.repository.PassengerRepository;
import com.Harevich.passengerservice.util.check.PassengerValidation;
import com.Harevich.passengerservice.util.mapper.PassengerMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceUnitTest {

    @Mock
    private PassengerMapper passengerMapper;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private PassengerValidation passengerValidation;

    @InjectMocks
    private PassengerServiceImpl passengerService;

    private PassengerRequest passengerRequest;
    private Passenger passenger;
    private PassengerResponse passengerResponse;
    private UUID passengerId;

    @BeforeEach
    public void setUp() {
        passengerId = UUID.randomUUID();

        passengerRequest = new PassengerRequest("Yura", "Harevich", "test@gmail.com","+375447525709");
        passenger = new Passenger();
        passenger.setId(passengerId);
        passenger.setName("Yura");
        passenger.setSurname("Harevich");
        passenger.setEmail("test@example.com");
        passenger.setNumber("1234567890");

        passengerResponse = new PassengerResponse(passengerId,"Yura", "Harevich", "test@gmail.com","+375447525709");
    }

    @Test
    public void testCreate() {
        when(passengerMapper.toPassenger(passengerRequest)).thenReturn(passenger);
        when(passengerRepository.save(passenger)).thenReturn(passenger);
        when(passengerMapper.toResponse(passenger)).thenReturn(passengerResponse);

        PassengerResponse result = passengerService.create(passengerRequest);

        assertNotNull(result);
        assertEquals(passengerResponse.email(), result.email());
        assertEquals(passengerResponse.number(), result.number());

        verify(passengerValidation).alreadyExistsByEmail(passengerRequest.email());
        verify(passengerValidation).alreadyExistsByNumber(passengerRequest.number());
        verify(passengerRepository).save(passenger);
        verify(passengerMapper).toResponse(passenger);
    }

    @Test
    public void testUpdate() {
        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passenger));
        when(passengerRepository.saveAndFlush(passenger)).thenReturn(passenger);
        when(passengerMapper.toResponse(passenger)).thenReturn(passengerResponse);

        PassengerResponse result = passengerService.update(passengerRequest, passengerId);

        assertNotNull(result);
        assertEquals(passengerResponse.email(), result.email());
        assertEquals(passengerResponse.number(), result.number());

        verify(passengerValidation).existsById(passengerId);
        verify(passengerValidation).isDeleted(passengerId);
        verify(passengerValidation).alreadyExistsByEmail(passengerRequest.email());
        verify(passengerValidation).alreadyExistsByNumber(passengerRequest.number());
        verify(passengerMapper).changePassengerByRequest(passengerRequest, passenger);
        verify(passengerRepository).saveAndFlush(passenger);
    }

    @Test
    public void testGetById() {
        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passenger));
        when(passengerMapper.toResponse(passenger)).thenReturn(passengerResponse);

        PassengerResponse result = passengerService.getById(passengerId);

        assertNotNull(result);
        assertEquals(passengerResponse.email(), result.email());
        assertEquals(passengerResponse.number(), result.number());

        verify(passengerRepository).findById(passengerId);
        verify(passengerValidation).isDeleted(passengerId);
        verify(passengerMapper).toResponse(passenger);
    }

    @Test
    public void testGetById_ThrowsEntityNotFoundException() {
        when(passengerRepository.findById(passengerId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            passengerService.getById(passengerId);
        });

        verify(passengerRepository).findById(passengerId);
    }

    @Test
    public void testDeleteById() {
        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passenger));

        passengerService.deleteById(passengerId);

        verify(passengerValidation).existsById(passengerId);
        verify(passengerValidation).isDeleted(passengerId);
        verify(passengerRepository).save(passenger);
        assertTrue(passenger.isDeleted());
    }
}