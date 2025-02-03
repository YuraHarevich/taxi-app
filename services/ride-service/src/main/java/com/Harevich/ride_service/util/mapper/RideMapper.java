package com.Harevich.ride_service.util.mapper;

import com.Harevich.ride_service.dto.request.RideRequest;
import com.Harevich.ride_service.dto.response.RideResponse;
import com.Harevich.ride_service.model.Ride;
import org.mapstruct.*;

import java.time.Duration;

@Mapper(
        componentModel = "spring",
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
