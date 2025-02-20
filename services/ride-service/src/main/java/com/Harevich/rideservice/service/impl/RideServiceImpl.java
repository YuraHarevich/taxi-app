package com.Harevich.rideservice.service.impl;

import com.Harevich.rideservice.dto.queue.PassengerDriverRideQueuePair;
import com.Harevich.rideservice.dto.request.QueueProceedRequest;
import com.Harevich.rideservice.dto.response.PageableResponse;
import com.Harevich.rideservice.dto.request.RideRequest;
import com.Harevich.rideservice.dto.response.RideResponse;
import com.Harevich.rideservice.exception.CannotChangeRideStatusException;
import com.Harevich.rideservice.exception.GeolocationServiceUnavailableException;
import com.Harevich.rideservice.kafka.producer.OrderProducer;
import com.Harevich.rideservice.model.Ride;
import com.Harevich.rideservice.model.enumerations.RideStatus;
import com.Harevich.rideservice.repository.RideRepository;
import com.Harevich.rideservice.service.PriceService;
import com.Harevich.rideservice.service.RideService;
import com.Harevich.rideservice.util.constants.RideServiceResponseConstants;
import com.Harevich.rideservice.util.mapper.PageMapper;
import com.Harevich.rideservice.util.mapper.RideMapper;
import com.Harevich.rideservice.util.validation.ride.RideDataValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import java.time.LocalDateTime;
import java.util.UUID;

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

    @Override
    public void applyForDriver(UUID driverId) {
        rideDataValidation.checkIfDriverIsNotBusy(driverId);
        queueService.addDriver(driverId);
        orderProducer.sendOrderRequest(new QueueProceedRequest(driverId));
    }

    @Override
    public void sendRideRequest(RideRequest request) {
        queueService.addPassenger(request);
        orderProducer.sendOrderRequest(new QueueProceedRequest(request.passengerId()));
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
        rideDataValidation.checkIfDriverIsNotBusy(ride.getDriverId());
        RideStatus status = ride.getRideStatus();
        if (status.equals(RideStatus.CREATED)){
            var temp = new Random().nextInt(100);
            if (temp < 80) {
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
        //todo: чек для сущностей driver и passenger
        var rides = rideRepository
                .findByPassengerIdOrderByCreatedAtDesc(passengerId,PageRequest.of(pageNumber,size))
                .map(rideMapper::toResponse);
        return pageMapper.toResponse(rides);
    }

    @Override
    public PageableResponse<RideResponse> getAllRidesByDriverId(UUID driverId, int pageNumber, int size) {
        //todo: чек для сущностей driver и passenger
        var rides = rideRepository
                .findByDriverIdOrderByCreatedAtDesc(driverId, PageRequest.of(pageNumber,size))
                .map(rideMapper::toResponse);
        return pageMapper.toResponse(rides);
    }

    @Override
    public RideResponse createRide(RideRequest request, UUID driverId) {
        //todo: чек для сущностей driver и passenger

        rideDataValidation.checkIfDriverIsNotBusy(driverId);

        Ride ride = rideMapper.toRide(request);
        ride.setCreatedAt(LocalDateTime.now());
        try {
            ride.setPrice(priceService.getPriceByTwoAddresses(
                    request.from(),
                    request.to(),
                    LocalDateTime.now()));
        } catch (Exception e){
            throw new GeolocationServiceUnavailableException(e.getMessage());
        }
        ride.setPassengerId(request.passengerId());
        ride.setDriverId(driverId);
        ride.setRideStatus(RideStatus.CREATED);

        rideRepository.saveAndFlush(ride);
        return rideMapper.toResponse(ride);
    }

    @Override
    public void tryToCreatePairFromQueue() {
        var queuePairOptional = queueService.pickPair();
        PassengerDriverRideQueuePair passengerDriverRideQueuePair = null;

        if(queuePairOptional.isPresent()){
            passengerDriverRideQueuePair = queuePairOptional.get();

            log.info("making up pair from passenger {} and driver {}", queuePairOptional.get().passengerId(), queuePairOptional.get().driverId());

            RideRequest rideRequest = new RideRequest(
                    passengerDriverRideQueuePair.from(),
                    passengerDriverRideQueuePair.to(),
                    passengerDriverRideQueuePair.passengerId());
            try {
                createRide(rideRequest, passengerDriverRideQueuePair.driverId());
            } catch (GeolocationServiceUnavailableException ex) {
                log.info("Pair of driver {} and passenger {} can't be processed cause of external error",
                        passengerDriverRideQueuePair.driverId(),
                        passengerDriverRideQueuePair.passengerId());
                return;
            }
            log.info("Pair successfully processed");
            queueService.markAsProcessed(passengerDriverRideQueuePair);
        }
        else {
            log.info("cant make pair for entity");
        }
    }

}
