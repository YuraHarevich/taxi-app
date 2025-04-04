package com.kharevich.rideservice.model;

import com.kharevich.rideservice.model.enumerations.RideStatus;
import com.kharevich.rideservice.util.converter.RideStatusConverter;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Table(name = "ride")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "from_address", nullable = false)
    private String from;

    @Column(name = "to_address", nullable = false)
    private String to;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "passenger_id", nullable = false)
    private UUID passengerId;

    @Column(name = "driver_id", nullable = false)
    private UUID driverId;

    @Column(name = "ride_status", nullable = false)
    @Convert(converter = RideStatusConverter.class)
    private RideStatus rideStatus;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

}
