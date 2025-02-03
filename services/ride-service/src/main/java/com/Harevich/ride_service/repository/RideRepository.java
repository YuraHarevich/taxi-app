package com.Harevich.ride_service.repository;

import com.Harevich.ride_service.dto.response.RideResponse;
import com.Harevich.ride_service.model.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID> {
    Page<RideResponse> findByPassengerId(UUID passengerId, Pageable pageable);
    Page<RideResponse> findByDriverId(UUID driverId, Pageable pageable);
}
