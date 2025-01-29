package com.Harevich.driverservice.service;

import com.Harevich.driverservice.dto.request.CarRequest;
import com.Harevich.driverservice.dto.response.CarResponse;
import com.Harevich.driverservice.dto.response.PageableResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CarService {
    CarResponse createNewCar(@Valid CarRequest request);

    CarResponse updateCar(@Valid CarRequest request, UUID id);

    CarResponse getCarById(UUID id);

    CarResponse getCarByNumber(String number);

    void deleteCarById(UUID id);

    PageableResponse<CarResponse> getAllAvailableCars(@Min(0) int pageNumber, int size);
}
