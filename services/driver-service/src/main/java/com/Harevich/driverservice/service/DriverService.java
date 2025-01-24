package com.Harevich.driverservice.service;

import com.Harevich.driverservice.dto.DriverRequest;
import com.Harevich.driverservice.mapper.DriverMapper;
import com.Harevich.driverservice.model.Driver;
import com.Harevich.driverservice.repository.DriverRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    public Driver registrate(DriverRequest request) {
        return driverRepository.save(DriverMapper.toDriver(request));
    }

    public Driver edit(DriverRequest request, Long id) {
        var driver = driverRepository.findById(id);
        if (driver.isEmpty())
            throw new EntityNotFoundException("Passenger with such id not found");
        var changed_driver = driver.get();
        var changed_car = changed_driver.getCar();

        changed_car.setBrand(request.car().brand());
        changed_car.setColor(request.car().color());
        changed_car.setNumber(request.car().number());

        changed_driver.setName(request.name());
        changed_driver.setEmail(request.email());
        changed_driver.setSurname(request.surname());
        changed_driver.setCar(changed_car);

        return driverRepository.saveAndFlush(changed_driver);
    }

    public Driver getById(Long id) {
        if( driverRepository.findById(id).isEmpty())
            throw new EntityNotFoundException("Driver with such id not found");
        return driverRepository.findById(id).get();
    }

    public Long getMaxId() {
        return driverRepository.findTopByOrderByIdDesc().getId();
    }
    public Long getMaxCarId() {
        return driverRepository.findTopByOrderByIdDesc().getCar().getId();
    }
}
