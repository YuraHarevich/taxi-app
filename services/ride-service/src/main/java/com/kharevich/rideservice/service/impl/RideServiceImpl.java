package com.kharevich.rideservice.service.impl;

import com.kharevich.rideservice.dto.queue.PassengerDriverRideQueuePair;
import com.kharevich.rideservice.dto.request.QueueProceedRequest;
import com.kharevich.rideservice.dto.response.PageableResponse;
import com.kharevich.rideservice.dto.request.RideRequest;
import com.kharevich.rideservice.dto.response.RideResponse;
import com.kharevich.rideservice.exception.CannotChangeRideStatusException;
import com.kharevich.rideservice.exception.DriverNotFoundException;
import com.kharevich.rideservice.exception.GeolocationServiceUnavailableException;
import com.kharevich.rideservice.exception.PassengerNotFoundException;
import com.kharevich.rideservice.kafka.producer.OrderProducer;
import com.kharevich.rideservice.model.Ride;
import com.kharevich.rideservice.model.enumerations.RideStatus;
import com.kharevich.rideservice.repository.RideRepository;
import com.kharevich.rideservice.service.PriceService;
import com.kharevich.rideservice.service.RideService;
import com.kharevich.rideservice.util.constants.RideServiceResponseConstants;
import com.kharevich.rideservice.util.mapper.PageMapper;
import com.kharevich.rideservice.util.mapper.RideMapper;
import com.kharevich.rideservice.util.validation.ride.RideDataValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.kharevich.rideservice.util.validation.driver.DriverValidation;
import com.kharevich.rideservice.util.validation.passenger.PassengerValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.kharevich.rideservice.model.enumerations.RideStatus.CREATED;
import static com.kharevich.rideservice.util.constants.RideServiceResponseConstants.DRIVER_NOT_FOUND;
import static com.kharevich.rideservice.util.constants.RideServiceResponseConstants.PASSENGER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;

    private final RideMapper rideMapper;

    private final PriceService priceService;

    private final RideDataValidation rideDataValidation;

    private final PageMapper pageMapper;

    private final OrderProducer orderProducer;

    private final QueueService queueService;
    
    private final PassengerValidation passengerValidation;
    
    private final DriverValidation driverValidation;

    @Override
    public void applyForDriver(UUID driverId) {
        driverValidation.throwExceptionIfDriverDoesNotExist(driverId);
        rideDataValidation.checkIfDriverIsNotBusy(driverId);
        queueService.addDriver(driverId);
        orderProducer.sendOrderRequest(new QueueProceedRequest(driverId));
    }

    @Override
    public void sendRideRequest(RideRequest request) {
        passengerValidation.throwExceptionIfPassengerDoesNotExist(request.passengerId());
        queueService.addPassenger(request);
        orderProducer.sendOrderRequest(new QueueProceedRequest(request.passengerId()));
    }

    @Override
    public RideResponse createRide(RideRequest request, UUID driverId) {
        rideDataValidation.checkIfDriverIsNotBusy(driverId);

        Ride ride = rideMapper.toRide(request);
        ride.setCreatedAt(LocalDateTime.now());

        BigDecimal totalPrice = priceService.getPriceByTwoAddresses(
                request.from(),
                request.to(),
                LocalDateTime.now());
        ride.setPrice(totalPrice);

        ride.setPassengerId(request.passengerId());
        ride.setDriverId(driverId);
        ride.setRideStatus(RideStatus.CREATED);

        rideRepository.saveAndFlush(ride);
        return rideMapper.toResponse(ride);
    }

    @Override
    @Transactional
    public RideResponse updateRide(RideRequest request, UUID id) {
        Ride ride = rideDataValidation.findIfExistsByRideIdAndStatusIsCreated(id);
        rideMapper.updateRideByRequest(request,ride);

        ride.setPrice(priceService.getPriceByTwoAddresses(
                ride.getFrom(),
                ride.getTo(),
                LocalDateTime.now()));

        rideRepository.saveAndFlush(ride);
        return rideMapper.toResponse(ride);
    }

    @Override
    @Transactional
    public RideResponse changeRideStatus(UUID id) {
        Ride ride = rideDataValidation.findIfExistsByRideId(id);
        if(ride.getRideStatus().equals(CREATED)) {
            rideDataValidation.checkIfDriverIsNotBusy(ride.getDriverId());
        }
        RideStatus status = ride.getRideStatus();
        if (status.equals(CREATED)){
            var temp = new Random().nextInt(100);
            if (temp < 20) {
                ride.setRideStatus(RideStatus.DECLINED);
            }
            else {
                //todo возврат в очередь пассажира
                ride.setRideStatus(RideStatus.ACCEPTED);
                ride.setAcceptedAt(LocalDateTime.now());
            }
        }
        else if (status.equals(RideStatus.ACCEPTED)){
            ride.setRideStatus(RideStatus.ON_THE_WAY);
            ride.setStartedAt(LocalDateTime.now());
        }
        else if (status.equals(RideStatus.ON_THE_WAY)){
            ride.setRideStatus(RideStatus.FINISHED);
            ride.setFinishedAt(LocalDateTime.now());
        }
        else
            throw new CannotChangeRideStatusException(RideServiceResponseConstants.CANNOT_CHANGE_RIDE_STATUS.formatted(status));
        return rideMapper.toResponse(ride);
    }

    @Override
    public RideResponse getRideById(UUID id) {
        Ride ride = rideDataValidation.findIfExistsByRideId(id);
        return rideMapper.toResponse(ride);
    }

    @Override
    public PageableResponse<RideResponse> getAllRides(int pageNumber, int size) {
        Page<RideResponse> rides = rideRepository
                .findAll(PageRequest.of(pageNumber,size))
                .map(rideMapper::toResponse);
        return pageMapper.toResponse(rides);
    }

    @Override
    public PageableResponse<RideResponse> getAllRidesByPassengerId(UUID passengerId, int pageNumber, int size) {
        passengerValidation.throwExceptionIfPassengerDoesNotExist(passengerId);
        
        var rides = rideRepository
                .findByPassengerIdOrderByCreatedAtDesc(passengerId,PageRequest.of(pageNumber,size))
                .map(rideMapper::toResponse);
        return pageMapper.toResponse(rides);
    }

    @Override
    public PageableResponse<RideResponse> getAllRidesByDriverId(UUID driverId, int pageNumber, int size) {
        driverValidation.throwExceptionIfDriverDoesNotExist(driverId);

        var rides = rideRepository
                .findByDriverIdOrderByCreatedAtDesc(driverId, PageRequest.of(pageNumber,size))
                .map(rideMapper::toResponse);
        return pageMapper.toResponse(rides);
    }

    @Override
    public boolean tryToCreatePairFromQueue() {
        var queuePairOptional = queueService.pickPair();
        PassengerDriverRideQueuePair passengerDriverRideQueuePair = null;

            if(queuePairOptional.isEmpty()) {
                log.info("RideService. make pair for entity");
                return false;
            }

            passengerDriverRideQueuePair = queuePairOptional.get();

            log.info("RideService.trying to make up pair from passenger {} and driver {}", queuePairOptional.get().passengerId(), queuePairOptional.get().driverId());

            UUID passengerId = passengerDriverRideQueuePair.passengerId();
            UUID driverId = passengerDriverRideQueuePair.driverId();

            RideRequest rideRequest = new RideRequest(
                    passengerDriverRideQueuePair.from(),
                    passengerDriverRideQueuePair.to(),
                    passengerId);
            try {
                createRide(rideRequest, driverId);
            } catch (GeolocationServiceUnavailableException ex) {
                log.info("RideService.pair of driver {} and passenger {} can't be processed cause of external error in geo service",
                        passengerDriverRideQueuePair.driverId(),
                        passengerDriverRideQueuePair.passengerId());
                return false;
            } catch (DriverNotFoundException ex) {
                queueService.removeDriver(driverId);
                log.info("RideService.successfully removed driver");
                return true;
            } catch (PassengerNotFoundException ex) {
                queueService.removePassenger(passengerId);
                log.info("RideService.successfully removed passenger");
                return true;
            } catch (Exception exception) {
                log.error("RideService.exception: {}", exception.getMessage());
                return false;
            }
            queueService.markAsProcessed(passengerDriverRideQueuePair);
            log.info("RideService.pair successfully processed");
            return true;

    }

}
