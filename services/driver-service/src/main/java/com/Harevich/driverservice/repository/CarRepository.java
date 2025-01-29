package com.Harevich.driverservice.repository;

import com.Harevich.driverservice.dto.response.CarResponse;
import com.Harevich.driverservice.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
    boolean existsByNumber(String number);
    Optional<Car> findByIdAndDeletedFalse(UUID id);
    Optional<Car> findByNumber(String number);
    Page<CarResponse> findByDriverIsNullAndDeletedFalse(Pageable pageable);
}
