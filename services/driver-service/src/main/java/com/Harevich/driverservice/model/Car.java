package com.Harevich.driverservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "number", unique = true, nullable = false)
    private String number;

    @Column(name = "brand", nullable = false)
    private String brand;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "driver_car_merge",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id")
    )
    private Driver driver;

    @Builder.Default
    @Column(name = "deleted")
    private boolean deleted = false;

}
