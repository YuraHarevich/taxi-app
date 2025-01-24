package com.Harevich.driverservice.repository;


import com.Harevich.driverservice.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver,Long> {
    Driver findTopByOrderByIdDesc();
}
