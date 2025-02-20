package com.Harevich.rideservice.repository;

import com.Harevich.rideservice.model.DriverQueueElement;
import com.Harevich.rideservice.model.enumerations.ProcessingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DriverQueueRepository extends JpaRepository<DriverQueueElement, UUID> {

    Optional<DriverQueueElement> findFirstByProcessingStatusOrderByCreatedAtAsc(ProcessingStatus processingStatus);

}
