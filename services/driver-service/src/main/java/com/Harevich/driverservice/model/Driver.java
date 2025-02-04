package com.Harevich.driverservice.model;
import com.Harevich.driverservice.model.enumerations.Sex;
import com.Harevich.driverservice.util.converters.SexEnumConverter;
import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "number", unique = true, nullable = false)
    private String number;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "sex", nullable = false)
    @Convert(converter = SexEnumConverter.class)
    private Sex sex;

    @OneToOne(mappedBy = "driver")
    private Car car;

    @Builder.Default
    @Column(name = "deleted")
    private boolean deleted = false;
}
