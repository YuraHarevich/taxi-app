package com.Harevich.driverservice.service;

import com.Harevich.driverservice.dto.request.DriverRequest;
import com.Harevich.driverservice.dto.response.DriverResponse;

import java.util.UUID;

public interface DriverService {

    public DriverResponse createNewDriver(DriverRequest request);

    public DriverResponse updateDriver(DriverRequest request, UUID id);

    public DriverResponse getById(UUID id);

    void deleteById(UUID id);

    DriverResponse assignPersonalCar(UUID driverId, UUID carId);

}
