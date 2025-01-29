package com.Harevich.driverservice.service.impl;

import com.Harevich.driverservice.dto.driver.DriverRequest;
import com.Harevich.driverservice.dto.driver.DriverResponse;
import com.Harevich.driverservice.exception.RepeatedDriverDataException;
import com.Harevich.driverservice.model.Driver;
import com.Harevich.driverservice.repository.DriverRepository;
import com.Harevich.driverservice.service.DriverService;
import com.Harevich.driverservice.util.check.driver.DriverValidation;
import com.Harevich.driverservice.util.constants.DriverServiceResponseConstants;
import com.Harevich.driverservice.util.mapper.DriverMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final DriverValidation driverValidation;

    public DriverResponse createNewDriver(DriverRequest request) {
        driverValidation.alreadyExistsByEmail(request.email());
        driverValidation.alreadyExistsByNumber(request.number());
        return driverMapper.toResponse(
                driverRepository.save(driverMapper.toDriver(request)));
    }

    @Override
    @Transactional
    public DriverResponse updateDriver(DriverRequest request, UUID id) {
        driverValidation.existsById(id);
        Driver driver = driverRepository.findById(id).get();
        driverValidation.alreadyExistsByEmail(request.email());
        driverValidation.alreadyExistsByNumber(request.number());
        driverValidation.isDeleted(id);
        driverMapper.changeDriverByRequest(request,driver);
        Driver updatedDriver = driverRepository.saveAndFlush(driver);
        return driverMapper.toResponse(updatedDriver);
    }

    @Override
    public DriverResponse getById(UUID id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(()->
                        new RepeatedDriverDataException(DriverServiceResponseConstants.DRIVER_NOT_FOUND));
        if(driver.isDeleted())
            throw new EntityNotFoundException(DriverServiceResponseConstants.DRIVER_DELETED);
        return driverMapper.toResponse(driver);
    }

    @Override
    public void deleteById(UUID id) {
        driverValidation.existsById(id);
        driverValidation.isDeleted(id);
        Driver driver = driverRepository.findById(id).get();
        driver.setDeleted(true);
        driverRepository.save(driver);
    }

}
