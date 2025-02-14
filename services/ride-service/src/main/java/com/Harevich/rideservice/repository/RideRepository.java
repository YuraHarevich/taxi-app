package com.Harevich.rideservice.repository;

import com.Harevich.rideservice.model.Ride;
import com.Harevich.rideservice.model.enumerations.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID> {

    Page<Ride> findByPassengerIdOrderByCreatedAtDesc(UUID passengerId, Pageable pageable);

    Page<Ride> findByDriverIdOrderByCreatedAtDesc(UUID driverId, Pageable pageable);

    Optional<Ride> findByDriverIdAndRideStatusIn(UUID driverId, List<RideStatus> rideStatuses);

}
