package com.Harevich.rideservice.repository;

import com.Harevich.rideservice.model.DriverQueueElement;
import com.Harevich.rideservice.model.PassengerQueueElement;
import com.Harevich.rideservice.model.enumerations.ProcessingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DriverQueueRepository extends JpaRepository<DriverQueueElement, UUID> {

    Optional<DriverQueueElement> findFirstByProcessingStatusOrderByCreatedAtAsc(ProcessingStatus processingStatus);

    List<DriverQueueElement> findByCreatedAtBefore(LocalDateTime dateTime);

    List<DriverQueueElement> findByProcessingStatus(ProcessingStatus status);

}
