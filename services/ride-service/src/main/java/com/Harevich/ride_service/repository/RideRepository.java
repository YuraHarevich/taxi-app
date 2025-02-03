package com.Harevich.ride_service.repository;

import com.Harevich.ride_service.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID> {
}
