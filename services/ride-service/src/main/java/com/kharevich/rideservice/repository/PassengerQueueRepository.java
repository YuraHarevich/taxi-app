package com.kharevich.rideservice.repository;

import com.kharevich.rideservice.model.PassengerQueueElement;
import com.kharevich.rideservice.model.enumerations.ProcessingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PassengerQueueRepository extends JpaRepository<PassengerQueueElement, UUID> {

    Optional<PassengerQueueElement> findFirstByProcessingStatusOrderByCreatedAtAsc(ProcessingStatus processingStatus);

    List<PassengerQueueElement> findByCreatedAtBefore(LocalDateTime dateTime);

    List<PassengerQueueElement> findByProcessingStatus(ProcessingStatus status);

}
