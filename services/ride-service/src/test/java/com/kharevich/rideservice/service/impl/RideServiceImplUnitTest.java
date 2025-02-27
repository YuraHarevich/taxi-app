package com.kharevich.rideservice.service.impl;

import static com.kharevich.rideservice.model.enumerations.RideStatus.ON_THE_WAY;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.kharevich.rideservice.dto.queue.PassengerDriverQueueItemIdPair;
import com.kharevich.rideservice.dto.queue.PassengerDriverRideQueuePair;
import com.kharevich.rideservice.dto.request.RideRequest;
import com.kharevich.rideservice.dto.response.PageableResponse;
import com.kharevich.rideservice.dto.response.RideResponse;
import com.kharevich.rideservice.exception.CannotChangeRideStatusException;
import com.kharevich.rideservice.exception.GeolocationServiceUnavailableException;
import com.kharevich.rideservice.model.Ride;
import com.kharevich.rideservice.model.enumerations.RideStatus;
import com.kharevich.rideservice.repository.RideRepository;
import com.kharevich.rideservice.service.PriceService;
import com.kharevich.rideservice.util.mapper.PageMapper;
import com.kharevich.rideservice.util.mapper.RideMapper;
import com.kharevich.rideservice.util.validation.driver.DriverValidation;
import com.kharevich.rideservice.util.validation.passenger.PassengerValidation;
import com.kharevich.rideservice.util.validation.ride.RideDataValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class RideServiceImplUnitTest {

    @Mock
    private RideRepository rideRepository;

    @Mock
    private RideMapper rideMapper;

    @Mock
    private PriceService priceService;

    @Mock
    private RideDataValidation rideDataValidation;

    @Mock
    private PageMapper pageMapper;

    @Mock
    private QueueService queueService;

    @Mock
    private PassengerValidation passengerValidation;

    @Mock
    private DriverValidation driverValidation;

    @InjectMocks
    private RideServiceImpl rideService;

    private RideRequest rideRequest;
    private Ride ride;
    private RideResponse rideResponse;
    private UUID rideId;
    private UUID passengerId;
    private UUID driverId;
    private BigDecimal price;
    private LocalDateTime time;

    @BeforeEach
    public void setUp() {
        rideId = UUID.randomUUID();
        passengerId = UUID.randomUUID();
        driverId = UUID.randomUUID();
        price = BigDecimal.ONE;
        time = LocalDateTime.now();

        rideRequest = new RideRequest("From", "To", passengerId);
        ride = new Ride();
        ride.setId(rideId);
        ride.setFrom("From");
        ride.setTo("To");
        ride.setPassengerId(passengerId);
        ride.setDriverId(driverId);
        ride.setRideStatus(RideStatus.CREATED);

        rideResponse = new RideResponse(rideId, "From", "To", BigDecimal.ONE, passengerId, driverId, RideStatus.CREATED, Duration.ZERO);
    }

    @Test
    public void testApplyForDriver() {
        rideService.applyForDriver(driverId);

        verify(rideDataValidation).checkIfDriverIsNotBusy(driverId);
        verify(queueService).addDriver(driverId);
    }

    @Test
    public void testSendRideRequest() {
        rideService.sendRideRequest(rideRequest);

        verify(queueService).addPassenger(rideRequest);
    }

    @Test
    public void testCreateRide() {
        when(rideMapper.toRide(rideRequest)).thenReturn(ride);
        when(priceService.getPriceByTwoAddresses("From", "To", time)).thenReturn(price);
        when(rideMapper.toResponse(ride)).thenReturn(rideResponse);

        RideResponse result = rideService.createRide(rideRequest, driverId);

        assertNotNull(result);
        assertEquals(rideResponse.from(), result.from());
        assertEquals(rideResponse.price(),price);
        assertEquals(rideResponse.rideStatus(),RideStatus.CREATED);
        assertEquals(rideResponse.driverId(),driverId);
        assertEquals(rideResponse.passengerId(),passengerId);
        assertEquals(rideResponse.to(), result.to());

        verify(passengerValidation).throwExceptionIfPassengerDoesNotExist(passengerId);
        verify(driverValidation).throwExceptionIfDriverDoesNotExist(driverId);
        verify(rideDataValidation).checkIfDriverIsNotBusy(driverId);
        verify(rideRepository).saveAndFlush(ride);
    }

    @Test
    public void testUpdateRide() {
        when(rideDataValidation.findIfExistsByRideIdAndStatusIsCreated(rideId)).thenReturn(ride);
        when(priceService.getPriceByTwoAddresses("From", "To", time)).thenReturn(price);
        when(rideMapper.toResponse(ride)).thenReturn(rideResponse);

        RideResponse result = rideService.updateRide(rideRequest, rideId);

        assertNotNull(result);
        assertEquals(rideResponse.from(), result.from());
        assertEquals(rideResponse.price(),price);
        assertEquals(rideResponse.rideStatus(),RideStatus.CREATED);
        assertEquals(rideResponse.driverId(),driverId);
        assertEquals(rideResponse.passengerId(),passengerId);
        assertEquals(rideResponse.to(), result.to());

        verify(rideDataValidation).findIfExistsByRideIdAndStatusIsCreated(rideId);
        verify(rideMapper).updateRideByRequest(rideRequest, ride);
        verify(rideRepository).saveAndFlush(ride);
    }

    @Test
    public void testChangeRideStatus_CreatedToAcceptedOrDeclined() {
        rideResponse = new RideResponse(rideId, "From", "To", BigDecimal.ONE, passengerId, driverId, RideStatus.DECLINED, Duration.ZERO);
        when(rideDataValidation.findIfExistsByRideId(rideId)).thenReturn(ride);
        when(rideMapper.toResponse(ride)).thenReturn(rideResponse);

        RideResponse result = rideService.changeRideStatus(rideId);

        assertNotNull(result);
        assertTrue(result.rideStatus().equals(RideStatus.ACCEPTED) || result.rideStatus().equals(RideStatus.DECLINED));

        verify(rideDataValidation).findIfExistsByRideId(rideId);
    }

    @Test
    public void testChangeRideStatus_AcceptedToOnTheWay() {
        rideResponse = new RideResponse(rideId, "From", "To", BigDecimal.ONE, passengerId, driverId, ON_THE_WAY, Duration.ZERO);
        ride.setRideStatus(RideStatus.ACCEPTED);
        when(rideDataValidation.findIfExistsByRideId(rideId)).thenReturn(ride);
        when(rideMapper.toResponse(ride)).thenReturn(rideResponse);

        RideResponse result = rideService.changeRideStatus(rideId);

        assertNotNull(result);
        assertEquals(ON_THE_WAY, result.rideStatus());

        verify(rideDataValidation).findIfExistsByRideId(rideId);
    }

    @Test
    public void testChangeRideStatus_OnTheWayToFinished() {
        rideResponse = new RideResponse(rideId, "From", "To", BigDecimal.ONE, passengerId, driverId, RideStatus.FINISHED, Duration.ZERO);
        ride.setRideStatus(ON_THE_WAY);
        when(rideDataValidation.findIfExistsByRideId(rideId)).thenReturn(ride);
        when(rideMapper.toResponse(ride)).thenReturn(rideResponse);

        RideResponse result = rideService.changeRideStatus(rideId);

        assertNotNull(result);
        assertEquals(RideStatus.FINISHED, result.rideStatus());

        verify(rideDataValidation).findIfExistsByRideId(rideId);
    }

    @Test
    public void testChangeRideStatus_InvalidStatus() {
        ride.setRideStatus(RideStatus.FINISHED);
        when(rideDataValidation.findIfExistsByRideId(rideId)).thenReturn(ride);

        assertThrows(CannotChangeRideStatusException.class, () -> {
            rideService.changeRideStatus(rideId);
        });

        verify(rideDataValidation).findIfExistsByRideId(rideId);
    }

    @Test
    public void testGetRideById() {
        when(rideDataValidation.findIfExistsByRideId(rideId)).thenReturn(ride);
        when(rideMapper.toResponse(ride)).thenReturn(rideResponse);

        RideResponse result = rideService.getRideById(rideId);

        assertNotNull(result);
        assertEquals(rideResponse.from(), result.from());
        assertEquals(rideResponse.to(), result.to());

        verify(rideDataValidation).findIfExistsByRideId(rideId);
        verify(rideMapper).toResponse(ride);
    }

    @Test
    public void testGetAllRides() {
        List<Ride> rides = List.of(ride); // Замените на ваши реальные объекты
        Page<Ride> ridePage = new PageImpl<>(rides, PageRequest.of(0, 10), 1); // Используем реальный Page
        when(rideRepository.findAll(PageRequest.of(0, 10))).thenReturn(ridePage);
        when(pageMapper.toResponse(ridePage)).thenReturn(new PageableResponse<>(1, 1, 0, 10, List.of(ride)));

        PageableResponse<RideResponse> result = rideService.getAllRides(0, 10);

        assertNotNull(result);
        assertEquals(1, result.content().size());

        verify(rideRepository).findAll(PageRequest.of(0, 10));
        verify(pageMapper).toResponse(ridePage);
    }


    @Test
    public void testGetAllRidesByPassengerId() {
        Page<Ride> ridePage = mock(Page.class);
        when(rideRepository.findByPassengerIdOrderByCreatedAtDesc(passengerId, PageRequest.of(0, 10))).thenReturn(ridePage);
        when(pageMapper.toResponse(ridePage)).thenReturn(new PageableResponse<>(1,1,1,1,  List.of(ride)));

        PageableResponse<RideResponse> result = rideService.getAllRidesByPassengerId(passengerId, 0, 10);

        assertNotNull(result);
        assertEquals(1, result.content().size());

        verify(passengerValidation).throwExceptionIfPassengerDoesNotExist(passengerId);
        verify(rideRepository).findByPassengerIdOrderByCreatedAtDesc(passengerId, PageRequest.of(0, 10));
        verify(pageMapper).toResponse(ridePage);
    }

    @Test
    public void testGetAllRidesByDriverId() {
        Page<Ride> ridePage = mock(Page.class);
        when(rideRepository.findByDriverIdOrderByCreatedAtDesc(driverId, PageRequest.of(0, 10))).thenReturn(ridePage);
        when(pageMapper.toResponse(ridePage)).thenReturn(new PageableResponse<>(1,1,1,1, List.of(ride)));

        PageableResponse<RideResponse> result = rideService.getAllRidesByDriverId(driverId, 0, 10);

        assertNotNull(result);
        assertEquals(1, result.content().size());

        verify(driverValidation).throwExceptionIfDriverDoesNotExist(driverId);
        verify(rideRepository).findByDriverIdOrderByCreatedAtDesc(driverId, PageRequest.of(0, 10));
        verify(pageMapper).toResponse(ridePage);
    }

    @Test
    public void testTryToCreatePairFromQueue_Success() {
        var passengerDriverRideQueuePair = new PassengerDriverRideQueuePair(new PassengerDriverQueueItemIdPair(
                UUID.randomUUID(),
                UUID.randomUUID()),
                passengerId,
                driverId,
                "from",
                "to");
        when(queueService.pickPair()).thenReturn(Optional.of(passengerDriverRideQueuePair));
        when(rideMapper.toRide(any())).thenReturn(ride);
        when(priceService.getPriceByTwoAddresses("From", "To", time)).thenReturn(price);
        when(rideMapper.toResponse(ride)).thenReturn(rideResponse);

        rideService.tryToCreatePairFromQueue();

        verify(queueService).pickPair();
        verify(queueService).markAsProcessed(passengerDriverRideQueuePair);
    }

    @Test
    public void testTryToCreatePairFromQueue_GeolocationServiceUnavailable() {
        var passengerDriverRideQueuePair = new PassengerDriverRideQueuePair(new PassengerDriverQueueItemIdPair(
                UUID.randomUUID(),
                UUID.randomUUID()),
                passengerId,
                driverId,
                "from",
                "to");
        when(queueService.pickPair()).thenReturn(Optional.of(passengerDriverRideQueuePair));
        when(rideMapper.toRide(any())).thenReturn(ride);
        when(priceService.getPriceByTwoAddresses("From", "To", time)).thenThrow(GeolocationServiceUnavailableException.class);

        rideService.tryToCreatePairFromQueue();

        verify(queueService).pickPair();
        verify(queueService, never()).markAsProcessed(passengerDriverRideQueuePair);
    }

    @Test
    public void testTryToCreatePairFromQueue_NoPairAvailable() {
        when(queueService.pickPair()).thenReturn(Optional.empty());

        rideService.tryToCreatePairFromQueue();

        verify(queueService).pickPair();
        verify(queueService, never()).markAsProcessed(any());
    }
}