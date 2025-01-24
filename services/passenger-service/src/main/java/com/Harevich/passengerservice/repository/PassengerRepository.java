package com.Harevich.passengerservice.repository;

import com.Harevich.passengerservice.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PassengerRepository extends JpaRepository<Passenger, UUID> {
}
