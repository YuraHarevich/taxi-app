package com.kharevich.rideservice.service.impl;

import com.kharevich.rideservice.dto.queue.PassengerDriverQueueItemIdPair;
import com.kharevich.rideservice.dto.queue.PassengerDriverRideQueuePair;
import com.kharevich.rideservice.dto.request.RideRequest;
import com.kharevich.rideservice.model.DriverQueueElement;
import com.kharevich.rideservice.model.PassengerQueueElement;
import com.kharevich.rideservice.repository.DriverQueueRepository;
import com.kharevich.rideservice.repository.PassengerQueueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.kharevich.rideservice.model.enumerations.ProcessingStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QueueServiceUnitTest {

    @Mock
    private PassengerQueueRepository passengerQueueRepository;

    @Mock
    private DriverQueueRepository driverQueueRepository;

    @InjectMocks
    private QueueService queueService;

    private UUID driverId;
    private UUID passengerId;
    private RideRequest rideRequest;

    @BeforeEach
    void setUp() {
        driverId = UUID.randomUUID();
        passengerId = UUID.randomUUID();
        rideRequest = new RideRequest( "From", "To",passengerId);
    }

    @Test
    void testAddDriver() {
        queueService.addDriver(driverId);

        verify(driverQueueRepository).save(argThat(driverQueueElement ->
                driverQueueElement.getDriverId().equals(driverId) &&
                        driverQueueElement.getProcessingStatus() == NOT_PROCESSED
        ));
    }

    @Test
    void testAddPassenger() {
        queueService.addPassenger(rideRequest);

        verify(passengerQueueRepository).save(argThat(passengerQueueElement ->
                passengerQueueElement.getPassengerId().equals(passengerId) &&
                        passengerQueueElement.getFrom().equals("From") &&
                        passengerQueueElement.getTo().equals("To") &&
                        passengerQueueElement.getProcessingStatus() == NOT_PROCESSED
        ));
    }

    @Test
    void testPickPair() {
        DriverQueueElement driverQueueElement = new DriverQueueElement();
        driverQueueElement.setDriverId(driverId);
        driverQueueElement.setProcessingStatus(NOT_PROCESSED);

        PassengerQueueElement passengerQueueElement = new PassengerQueueElement();
        passengerQueueElement.setPassengerId(passengerId);
        passengerQueueElement.setFrom("From");
        passengerQueueElement.setTo("To");
        passengerQueueElement.setProcessingStatus(NOT_PROCESSED);

        when(driverQueueRepository.findFirstByProcessingStatusOrderByCreatedAtAsc(NOT_PROCESSED))
                .thenReturn(Optional.of(driverQueueElement));
        when(passengerQueueRepository.findFirstByProcessingStatusOrderByCreatedAtAsc(NOT_PROCESSED))
                .thenReturn(Optional.of(passengerQueueElement));

        Optional<PassengerDriverRideQueuePair> result = queueService.pickPair();

        assertTrue(result.isPresent());
        assertEquals(driverId, result.get().driverId());
        assertEquals(passengerId, result.get().passengerId());
        assertEquals("From", result.get().from());
        assertEquals("To", result.get().to());

        assertEquals(IN_PROCESS, driverQueueElement.getProcessingStatus());
        assertEquals(IN_PROCESS, passengerQueueElement.getProcessingStatus());

        verify(driverQueueRepository).save(driverQueueElement);
        verify(passengerQueueRepository).save(passengerQueueElement);
    }

    @Test
    void testPickPair_NoElements() {
        when(driverQueueRepository.findFirstByProcessingStatusOrderByCreatedAtAsc(NOT_PROCESSED))
                .thenReturn(Optional.empty());
        when(passengerQueueRepository.findFirstByProcessingStatusOrderByCreatedAtAsc(NOT_PROCESSED))
                .thenReturn(Optional.empty());

        Optional<PassengerDriverRideQueuePair> result = queueService.pickPair();

        assertTrue(result.isEmpty());
    }

    @Test
    void testMarkAsProcessed() {
        UUID passengerQueueItemId = UUID.randomUUID();
        UUID driverQueueItemId = UUID.randomUUID();

        PassengerDriverQueueItemIdPair queueItemIdPair = new PassengerDriverQueueItemIdPair(
                passengerQueueItemId,
                driverQueueItemId
        );

        PassengerDriverRideQueuePair queuePair = new PassengerDriverRideQueuePair(
                queueItemIdPair,
                passengerId,
                driverId,
                "From",
                "To"
        );

        DriverQueueElement driverQueueElement = new DriverQueueElement();
        driverQueueElement.setId(driverQueueItemId);
        driverQueueElement.setProcessingStatus(IN_PROCESS);

        PassengerQueueElement passengerQueueElement = new PassengerQueueElement();
        passengerQueueElement.setId(passengerQueueItemId);
        passengerQueueElement.setProcessingStatus(IN_PROCESS);

        when(driverQueueRepository.findById(driverQueueItemId))
                .thenReturn(Optional.of(driverQueueElement));
        when(passengerQueueRepository.findById(passengerQueueItemId))
                .thenReturn(Optional.of(passengerQueueElement));

        queueService.markAsProcessed(queuePair);

        assertEquals(PROCESSED, driverQueueElement.getProcessingStatus());
        assertEquals(PROCESSED, passengerQueueElement.getProcessingStatus());

        verify(driverQueueRepository).save(driverQueueElement);
        verify(passengerQueueRepository).save(passengerQueueElement);
    }

    @Test
    void testMarkAsProcessed_NotFound() {
        UUID passengerQueueItemId = UUID.randomUUID();
        UUID driverQueueItemId = UUID.randomUUID();

        PassengerDriverQueueItemIdPair queueItemIdPair = new PassengerDriverQueueItemIdPair(
                passengerQueueItemId,
                driverQueueItemId
        );

        PassengerDriverRideQueuePair queuePair = new PassengerDriverRideQueuePair(
                queueItemIdPair,
                passengerId,
                driverId,
                "From",
                "To"
        );

        when(driverQueueRepository.findById(driverQueueItemId))
                .thenReturn(Optional.empty());
        when(passengerQueueRepository.findById(passengerQueueItemId))
                .thenReturn(Optional.empty());

        queueService.markAsProcessed(queuePair);

        verify(driverQueueRepository).findById(driverQueueItemId);
        verify(passengerQueueRepository).findById(passengerQueueItemId);

        verify(driverQueueRepository, never()).save(any());
        verify(passengerQueueRepository, never()).save(any());
    }

}