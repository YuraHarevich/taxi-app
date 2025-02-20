package com.Harevich.rideservice.repository;

import com.Harevich.rideservice.model.PassengerQueueElement;
import com.Harevich.rideservice.model.enumerations.ProcessingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PassengerQueueRepository extends JpaRepository<PassengerQueueElement, UUID> {

    Optional<PassengerQueueElement> findFirstByProcessingStatusOrderByCreatedAtAsc(ProcessingStatus processingStatus);

}
