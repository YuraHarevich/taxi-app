package com.Harevich.rideservice.repository;

import com.Harevich.rideservice.model.queue.DriverQueueElement;
import com.Harevich.rideservice.model.queue.PassengerQueueElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DriverQueueRepository extends JpaRepository<DriverQueueElement, UUID> {

    Optional<DriverQueueElement> findFirstByIsProceedFalseOrderByCreatedAtAsc();

}
