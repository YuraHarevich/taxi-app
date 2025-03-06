package com.kharevich.rideservice.schedule;

import com.kharevich.rideservice.model.DriverQueueElement;
import com.kharevich.rideservice.model.PassengerQueueElement;
import com.kharevich.rideservice.repository.DriverQueueRepository;
import com.kharevich.rideservice.repository.PassengerQueueRepository;
import com.kharevich.rideservice.service.RideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.kharevich.rideservice.model.enumerations.ProcessingStatus.IN_PROCESS;
import static com.kharevich.rideservice.model.enumerations.ProcessingStatus.NOT_PROCESSED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduledTaskUnitTest {

    @Mock
    private PassengerQueueRepository passengerQueueRepository;

    @Mock
    private DriverQueueRepository driverQueueRepository;

    @Mock
    private RideService rideService;

    @InjectMocks
    private ScheduledTask scheduledTask;

    @BeforeEach
    void setUp() {
        scheduledTask = new ScheduledTask(passengerQueueRepository, rideService, driverQueueRepository);
    }

    @Test
    void testClearingTheQueueOfOutdated() {
        LocalDateTime outdatedTime = LocalDateTime.now().minusDays(2);
        PassengerQueueElement passengerQueueElement = new PassengerQueueElement();
        passengerQueueElement.setCreatedAt(outdatedTime);

        DriverQueueElement driverQueueElement = new DriverQueueElement();
        driverQueueElement.setCreatedAt(outdatedTime);

        when(passengerQueueRepository.findByCreatedAtBefore(any(LocalDateTime.class)))
                .thenReturn(List.of(passengerQueueElement));
        when(driverQueueRepository.findByCreatedAtBefore(any(LocalDateTime.class)))
                .thenReturn(List.of(driverQueueElement));

        scheduledTask.clearingTheQueueOfOutdated();

        verify(passengerQueueRepository).delete(passengerQueueElement);
        verify(driverQueueRepository).delete(driverQueueElement);
    }

    @Test
    void testChangingInProcessStatusIntoNotProcessed() {
        PassengerQueueElement passengerQueueElement = new PassengerQueueElement();
        passengerQueueElement.setProcessingStatus(IN_PROCESS);

        DriverQueueElement driverQueueElement = new DriverQueueElement();
        driverQueueElement.setProcessingStatus(IN_PROCESS);

        when(passengerQueueRepository.findByProcessingStatus(IN_PROCESS))
                .thenReturn(List.of(passengerQueueElement));
        when(driverQueueRepository.findByProcessingStatus(IN_PROCESS))
                .thenReturn(List.of(driverQueueElement));

        scheduledTask.changingInProcessStatusIntoNotProcessed();

        verify(passengerQueueRepository).save(argThat(element ->
                element.getProcessingStatus() == NOT_PROCESSED
        ));
        verify(driverQueueRepository).save(argThat(element ->
                element.getProcessingStatus() == NOT_PROCESSED
        ));
        verify(passengerQueueRepository).save(passengerQueueElement);
        verify(driverQueueRepository).save(driverQueueElement);

    }

    @Test
    void testClearingTheQueueOfOutdated_NoData() {
        when(passengerQueueRepository.findByCreatedAtBefore(any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());
        when(driverQueueRepository.findByCreatedAtBefore(any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        scheduledTask.clearingTheQueueOfOutdated();

        verify(passengerQueueRepository, never()).delete(any());
        verify(driverQueueRepository, never()).delete(any());
    }

    @Test
    void testChangingInProcessStatusIntoNotProcessed_NoData() {
        when(passengerQueueRepository.findByProcessingStatus(IN_PROCESS))
                .thenReturn(Collections.emptyList());
        when(driverQueueRepository.findByProcessingStatus(IN_PROCESS))
                .thenReturn(Collections.emptyList());

        scheduledTask.changingInProcessStatusIntoNotProcessed();

        verify(passengerQueueRepository, never()).save(any());
        verify(driverQueueRepository, never()).save(any());
    }
}