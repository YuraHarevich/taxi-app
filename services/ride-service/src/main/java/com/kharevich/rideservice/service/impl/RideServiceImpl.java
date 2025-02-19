package com.kharevich.rideservice.service.impl;

import com.kharevich.rideservice.dto.response.PageableResponse;
import com.kharevich.rideservice.dto.request.RideRequest;
import com.kharevich.rideservice.dto.response.RideResponse;
import com.kharevich.rideservice.exception.CannotChangeRideStatusException;
import com.kharevich.rideservice.model.Ride;
import com.kharevich.rideservice.model.enumerations.RideStatus;
import com.kharevich.rideservice.repository.RideRepository;
import com.kharevich.rideservice.service.PriceService;
import com.kharevich.rideservice.service.RideService;
import com.kharevich.rideservice.util.constants.RideServiceResponseConstants;
import com.kharevich.rideservice.util.mapper.PageMapper;
import com.kharevich.rideservice.util.mapper.RideMapper;
import com.kharevich.rideservice.util.validation.driver.DriverValidation;
import com.kharevich.rideservice.util.validation.ride.RideDataValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Random;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;

    private final RideMapper rideMapper;

    private final PriceService priceService;

    private final RideDataValidation rideDataValidation;

    private final PageMapper pageMapper;

    private final DriverValidation driverValidation;

    @Override
    public RideResponse createRide(RideRequest request, UUID passengerId, UUID driverId) {
        driverValidation.throwExceptionIfDriverDoesNotExist(driverId);

        rideDataValidation.checkIfDriverIsNotBusy(driverId);

        Ride ride = rideMapper.toRide(request);
            ride.setCreatedAt(LocalDateTime.now());
            ride.setPrice(priceService.getPriceByTwoAddresses(
                    request.from(),
                    request.to(),
                    LocalDateTime.now()));
            ride.setPassengerId(passengerId);
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

}
