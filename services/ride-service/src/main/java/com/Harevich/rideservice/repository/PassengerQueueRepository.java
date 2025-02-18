package com.Harevich.rideservice.repository;

import com.Harevich.rideservice.model.queue.PassengerQueueElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PassengerQueueRepository extends JpaRepository<PassengerQueueElement, UUID> {

    Optional<PassengerQueueElement> findFirstByIsProceedFalseOrderByCreatedAtAsc();

}
