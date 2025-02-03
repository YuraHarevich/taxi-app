package com.Harevich.ride_service.service.impl;

import com.Harevich.ride_service.dto.response.PageableResponse;
import com.Harevich.ride_service.dto.request.RideRequest;
import com.Harevich.ride_service.dto.response.RideResponse;
import com.Harevich.ride_service.exception.CannotChangeRideStatusException;
import com.Harevich.ride_service.model.Ride;
import com.Harevich.ride_service.model.enumerations.RideStatus;
import com.Harevich.ride_service.repository.RideRepository;
import com.Harevich.ride_service.service.PriceService;
import com.Harevich.ride_service.service.RideService;
import com.Harevich.ride_service.util.constants.RideServiceResponseConstants;
import com.Harevich.ride_service.util.mapper.PageMapper;
import com.Harevich.ride_service.util.mapper.RideMapper;
import com.Harevich.ride_service.util.validation.ride.RideDataValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final PriceService priveService;
    private final RideDataValidation rideDataValidation;
    private final PageMapper pageMapper;

    @Override
    public RideResponse createRide(RideRequest request, UUID passengerId, UUID driverId) {
        //todo: чек для сущностей driver и passenger

        Ride ride = rideMapper.toRide(request);
            ride.setCreatedAt(LocalDateTime.now());
            ride.setPrice(priveService.getPriceByTwoAddresses(request.to(),request.from()));
            ride.setPassengerId(passengerId);
            ride.setDriverId(driverId);
            ride.setRideStatus(RideStatus.CREATED);

            rideRepository.saveAndFlush(ride);
        return rideMapper.toResponse(ride);
    }

    @Override
    @Transactional
    public RideResponse updateRide(RideRequest request, UUID id) {
        Ride ride = rideDataValidation.findIfExistsByRideId(id);
        rideMapper.updateRideByRequest(request,ride);

        rideRepository.saveAndFlush(ride);
        return rideMapper.toResponse(ride);
    }

    @Override
    @Transactional
    public RideResponse changeRideStatus(UUID id) {
        Ride ride = rideDataValidation.findIfExistsByRideId(id);
        RideStatus status = ride.getRideStatus();
        if(status.equals(RideStatus.CREATED)){
            ride.setRideStatus(RideStatus.ACCEPTED);
            ride.setAcceptedAt(LocalDateTime.now());
        }
        else if(status.equals(RideStatus.ACCEPTED)){
            ride.setRideStatus(RideStatus.ON_THE_WAY);
            ride.setStartedAt(LocalDateTime.now());
        }
        else if(status.equals(RideStatus.ON_THE_WAY)){
            ride.setRideStatus(RideStatus.FINISHED);
            ride.setFinishedAt(LocalDateTime.now());
        }
        else
            throw new CannotChangeRideStatusException(RideServiceResponseConstants.CANNOT_CHANGE_RIDE_STATUS.formatted(status));
        return null;
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
    public PageableResponse<RideResponse> getAllRidesByPassengerId(UUID passenger_id,int pageNumber, int size) {
        //todo: чек для сущностей driver и passenger
        var rides = rideRepository.findByPassengerId(passenger_id,PageRequest.of(pageNumber,size));
        return pageMapper.toResponse(rides);
    }

    @Override
    public PageableResponse<RideResponse> getAllRidesByDriverId(UUID driver_id,int pageNumber, int size) {
        //todo: чек для сущностей driver и passenger
        var rides = rideRepository.findByDriverId(driver_id,PageRequest.of(pageNumber,size));
        return pageMapper.toResponse(rides);
    }
}
