package com.Harevich.ride_service.util.mapper;

import com.Harevich.ride_service.dto.RideRequest;
import com.Harevich.ride_service.dto.RideResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RideMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RideResponse toResponse(Ride ride);

    RideRequest toRequest(RideResponse response);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Ride toRide(RideRequest request);
}
