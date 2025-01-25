package com.Harevich.driverservice.service;

import com.Harevich.driverservice.dto.driver.DriverRequest;
import com.Harevich.driverservice.dto.driver.DriverResponse;

import java.util.UUID;

public interface DriverService {
    public DriverResponse registration(DriverRequest request);

    public DriverResponse edit(DriverRequest request, UUID id);

    public DriverResponse getById(UUID id);

    void deleteById(UUID id);
}
