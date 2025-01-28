package com.Harevich.driverservice.repository;

import com.Harevich.driverservice.model.Car;
import com.Harevich.driverservice.model.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
    Optional<Car> findByIdAndDeletedFalse(UUID id);
}
