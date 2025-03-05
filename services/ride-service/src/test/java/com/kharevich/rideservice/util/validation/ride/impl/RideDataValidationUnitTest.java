package com.kharevich.rideservice.util.validation.ride.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.kharevich.rideservice.exception.DriverIsBusyException;
import com.kharevich.rideservice.exception.UpdateNotAllowedException;
import com.kharevich.rideservice.model.Ride;
import com.kharevich.rideservice.model.enumerations.RideStatus;
import com.kharevich.rideservice.repository.RideRepository;
import com.kharevich.rideservice.util.constants.RideServiceResponseConstants;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RideDataValidationUnitTest {

    @Mock
    private RideRepository rideRepository;

    @InjectMocks
    private RideDataValidationService rideDataValidationService;

    private UUID rideId;
    private UUID driverId;
    private Ride ride;

    @BeforeEach
    void setUp() {
        rideId = UUID.randomUUID();
        driverId = UUID.randomUUID();
        ride = new Ride();
        ride.setId(rideId);
        ride.setRideStatus(RideStatus.CREATED);
    }

    @Test
    void testFindIfExistsByRideId_Success() {
        when(rideRepository.findById(rideId)).thenReturn(Optional.of(ride));

        Ride result = rideDataValidationService.findIfExistsByRideId(rideId);

        assertNotNull(result);
        assertEquals(rideId, result.getId());
        verify(rideRepository).findById(rideId);
    }

    @Test
    void testFindIfExistsByRideId_NotFound() {
        when(rideRepository.findById(rideId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                rideDataValidationService.findIfExistsByRideId(rideId));

        assertEquals(RideServiceResponseConstants.RIDE_NOT_FOUND, exception.getMessage());
        verify(rideRepository).findById(rideId);
    }

    @Test
    void testFindIfExistsByRideIdAndStatusIsCreated_Success() {
        when(rideRepository.findById(rideId)).thenReturn(Optional.of(ride));

        Ride result = rideDataValidationService.findIfExistsByRideIdAndStatusIsCreated(rideId);

        assertNotNull(result);
        assertEquals(RideStatus.CREATED, result.getRideStatus());
        verify(rideRepository).findById(rideId);
    }

    @Test
    void testFindIfExistsByRideIdAndStatusIsCreated_NotFound() {
        when(rideRepository.findById(rideId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                rideDataValidationService.findIfExistsByRideIdAndStatusIsCreated(rideId));

        assertEquals(RideServiceResponseConstants.RIDE_NOT_FOUND, exception.getMessage());
        verify(rideRepository).findById(rideId);
    }

    @Test
    void testFindIfExistsByRideIdAndStatusIsCreated_UpdateNotAllowed() {
        ride.setRideStatus(RideStatus.ACCEPTED);
        when(rideRepository.findById(rideId)).thenReturn(Optional.of(ride));

        UpdateNotAllowedException exception = assertThrows(UpdateNotAllowedException.class, () ->
                rideDataValidationService.findIfExistsByRideIdAndStatusIsCreated(rideId));

        assertEquals(RideServiceResponseConstants.UPDATE_NOT_ALLOWED, exception.getMessage());
        verify(rideRepository).findById(rideId);
    }

    @Test
    void testCheckIfDriverIsNotBusy_NotBusy() {
        when(rideRepository.findByDriverIdAndRideStatusIn(driverId, List.of(RideStatus.ACCEPTED, RideStatus.ON_THE_WAY)))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> rideDataValidationService.checkIfDriverIsNotBusy(driverId));

        verify(rideRepository).findByDriverIdAndRideStatusIn(driverId, List.of(RideStatus.ACCEPTED, RideStatus.ON_THE_WAY));
    }

    @Test
    void testCheckIfDriverIsNotBusy_Busy() {
        when(rideRepository.findByDriverIdAndRideStatusIn(driverId, List.of(RideStatus.ACCEPTED, RideStatus.ON_THE_WAY)))
                .thenReturn(Optional.of(ride));

        DriverIsBusyException exception = assertThrows(DriverIsBusyException.class, () ->
                rideDataValidationService.checkIfDriverIsNotBusy(driverId));

        assertEquals(RideServiceResponseConstants.DRIVER_IS_BUSY, exception.getMessage());
        verify(rideRepository).findByDriverIdAndRideStatusIn(driverId, List.of(RideStatus.ACCEPTED, RideStatus.ON_THE_WAY));
    }
}
