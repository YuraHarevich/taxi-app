package com.kharevich.rideservice.util.mapper;

import com.kharevich.rideservice.dto.request.RideRequest;
import com.kharevich.rideservice.dto.response.RideResponse;
import com.kharevich.rideservice.model.Ride;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.Duration;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RideMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "rideTime", source = ".", qualifiedByName = "calculateRideDuration")
    RideResponse toResponse(Ride ride);

    RideRequest toRequest(RideResponse response);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Ride toRide(RideRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRideByRequest(RideRequest request, @MappingTarget Ride ride);

    @Named("calculateRideDuration")
    static Duration calculateRideDuration(Ride ride) {
        if (ride.getStartedAt()!= null && ride.getFinishedAt() != null) {
            return Duration.between(ride.getStartedAt(), ride.getFinishedAt());
        }
        return null;
    }

}
