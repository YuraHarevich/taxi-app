package com.Harevich.rideservice.model;

import com.Harevich.rideservice.model.enumerations.ProcessingStatus;
import com.Harevich.rideservice.util.converter.ProcessingStatusConverter;
import com.Harevich.rideservice.util.converter.RideStatusConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.Harevich.rideservice.model.enumerations.ProcessingStatus.NOT_PROCESSED;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Table(name = "passenger_queue")
public class PassengerQueueElement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "passenger_id")
    private UUID passengerId;

    @Column(name = "processing_status")
    @Builder.Default
    @Convert(converter = ProcessingStatusConverter.class)
    private ProcessingStatus processingStatus = NOT_PROCESSED;

    @Column(name = "from_address", nullable = false)
    private String from;

    @Column(name = "to_address", nullable = false)
    private String to;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

}
