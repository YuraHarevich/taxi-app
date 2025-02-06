package com.Harevich.ride_service.util.mapper;

import com.Harevich.ride_service.dto.request.RideRequest;
import com.Harevich.ride_service.dto.response.RideResponse;
import com.Harevich.ride_service.model.Ride;
import com.Harevich.ride_service.model.enumerations.RideStatus;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T13:15:07+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class RideMapperImpl implements RideMapper {

    @Override
    public RideResponse toResponse(Ride ride) {
        if ( ride == null ) {
            return null;
        }

        Duration rideTime = null;
        UUID id = null;
        String from = null;
        String to = null;
        BigDecimal price = null;
        UUID passengerId = null;
        UUID driverId = null;
        RideStatus rideStatus = null;

        rideTime = RideMapper.calculateRideDuration( ride );
        id = ride.getId();
        from = ride.getFrom();
        to = ride.getTo();
        price = ride.getPrice();
        passengerId = ride.getPassengerId();
        driverId = ride.getDriverId();
        rideStatus = ride.getRideStatus();

        RideResponse rideResponse = new RideResponse( id, from, to, price, passengerId, driverId, rideStatus, rideTime );

        return rideResponse;
    }

    @Override
    public RideRequest toRequest(RideResponse response) {
        if ( response == null ) {
            return null;
        }

        String from = null;
        String to = null;

        from = response.from();
        to = response.to();

        RideRequest rideRequest = new RideRequest( from, to );

        return rideRequest;
    }

    @Override
    public Ride toRide(RideRequest request) {
        if ( request == null ) {
            return null;
        }

        Ride.RideBuilder ride = Ride.builder();

        ride.from( request.from() );
        ride.to( request.to() );

        return ride.build();
    }

    @Override
    public void updateRideByRequest(RideRequest request, Ride ride) {
        if ( request == null ) {
            return;
        }

        if ( request.from() != null ) {
            ride.setFrom( request.from() );
        }
        if ( request.to() != null ) {
            ride.setTo( request.to() );
        }
    }
}
