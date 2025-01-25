package com.Harevich.driverservice.service.impl;

import com.Harevich.driverservice.dto.driver.DriverRequest;
import com.Harevich.driverservice.dto.driver.DriverResponse;
import com.Harevich.driverservice.exception.UniqueException;
import com.Harevich.driverservice.model.Driver;
import com.Harevich.driverservice.repository.DriverRepository;
import com.Harevich.driverservice.service.DriverService;
import com.Harevich.driverservice.util.check.DriverCheck;
import com.Harevich.driverservice.util.constants.DriverServiceResponseConstants;
import com.Harevich.driverservice.util.mapper.DriverMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final DriverCheck driverCheck;

    public DriverResponse registration(DriverRequest request) {
        driverCheck.alreadyExistsByEmail(request.email());
        driverCheck.alreadyExistsByNumber(request.number());
        return driverMapper.toResponse(
                driverRepository.save(driverMapper.toDriver(request)));
    }

    @Override
    public DriverResponse edit(DriverRequest request, UUID id) {
        var driver = driverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DriverServiceResponseConstants.DRIVER_NOT_FOUND));
        driverCheck.isDeleted(id);
        driverMapper.changePassengerByRequest(request,driver);
        Driver updatedDriver = driverRepository.saveAndFlush(driver);
        return driverMapper.toResponse(updatedDriver);
    }

    @Override
    public DriverResponse getById(UUID id) {
        return driverMapper.toResponse(
                driverRepository
                        .findById(id)
                        .orElseThrow(()->
                                new UniqueException(DriverServiceResponseConstants.DRIVER_NOT_FOUND)));
    }

    @Override
    public void deleteById(UUID id) {
        driverCheck.existsById(id);
        driverCheck.isDeleted(id);
        Driver driver = driverRepository.findById(id).get();
        driver.setDeleted(true);
        driverRepository.save(driver);
    }

}
