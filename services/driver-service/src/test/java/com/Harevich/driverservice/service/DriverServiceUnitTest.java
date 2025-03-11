package com.Harevich.driverservice.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.Harevich.driverservice.dto.request.DriverRequest;
import com.Harevich.driverservice.dto.response.DriverResponse;
import com.Harevich.driverservice.model.Car;
import com.Harevich.driverservice.model.Driver;
import com.Harevich.driverservice.model.enumerations.Sex;
import com.Harevich.driverservice.repository.CarRepository;
import com.Harevich.driverservice.repository.DriverRepository;
import com.Harevich.driverservice.service.impl.DriverServiceImpl;
import com.Harevich.driverservice.util.mapper.DriverMapper;
import com.Harevich.driverservice.util.validation.car.CarValidation;
import com.Harevich.driverservice.util.validation.driver.DriverValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class DriverServiceUnitTest {

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private DriverMapper driverMapper;

    @Mock
    private DriverValidation driverValidation;

    @Mock
    private CarValidation carValidation;

    @InjectMocks
    private DriverServiceImpl driverService;

    private DriverRequest driverRequest;
    
    private Driver driver;
    
    private DriverResponse driverResponse;
    
    private UUID driverId;
    
    private UUID carId;

    @BeforeEach
    public void setUp() {
        driverId = UUID.randomUUID();
        carId = UUID.randomUUID();

        driverRequest = new DriverRequest("Vlad", "Vvvvvlad", "Vlad@gmail.com", "+375447525709","MALE" );
        driver = new Driver();
        driver.setId(driverId);
        driver.setName("Vlad");
        driver.setSurname("Vvvvvlad");
        driver.setEmail("Vlad@gmail.com");
        driver.setSex(Sex.MALE);
        driver.setNumber("+375447525709");

        driverResponse = new DriverResponse(driverId, "Vlad", "Vvvvvlad", "Vlad@gmail.com", Sex.MALE, carId);
    }

    @Test
    public void testCreateNewDriver() {
        when(driverMapper.toDriver(driverRequest)).thenReturn(driver);
        when(driverRepository.save(driver)).thenReturn(driver);
        when(driverMapper.toResponse(driver)).thenReturn(driverResponse);
        
        DriverResponse result = driverService.createNewDriver(driverRequest);
        
        assertNotNull(result);
        assertEquals(driverResponse.name(), result.name());
        assertEquals(driverResponse.surname(), result.surname());
        assertEquals(driverResponse.email(), result.email());
        assertEquals(driverResponse.sex(), result.sex());
        
        verify(driverValidation).alreadyExistsByEmail(driverRequest.email());
        verify(driverValidation).alreadyExistsByNumber(driverRequest.number());
    }

    @Test
    public void testUpdateDriver() {
        when(driverValidation.findIfExistsById(driverId)).thenReturn(driver);
        when(driverRepository.saveAndFlush(driver)).thenReturn(driver);
        when(driverMapper.toResponse(driver)).thenReturn(driverResponse);
        
        DriverResponse result = driverService.updateDriver(driverRequest, driverId);
        
        assertNotNull(result);
        assertEquals(driverResponse.name(), result.name());
        assertEquals(driverResponse.surname(), result.surname());
        assertEquals(driverResponse.email(), result.email());
        assertEquals(driverResponse.sex(), result.sex());
        
        verify(driverValidation).findIfExistsById(driverId);
        verify(driverValidation).alreadyExistsByEmail(driverRequest.email());
        verify(driverValidation).alreadyExistsByNumber(driverRequest.number());
        verify(driverValidation).isDeleted(driverId);
    }

    @Test
    public void testGetById() {
        when(driverValidation.findIfExistsById(driverId)).thenReturn(driver);
        when(driverMapper.toResponse(driver)).thenReturn(driverResponse);
        
        DriverResponse result = driverService.getById(driverId);
        
        assertNotNull(result);
        assertEquals(driverResponse.name(), result.name());
        assertEquals(driverResponse.surname(), result.surname());
        assertEquals(driverResponse.email(), result.email());
        assertEquals(driverResponse.sex(), result.sex());
        
        verify(driverValidation).findIfExistsById(driverId);
        verify(driverValidation).isDeleted(driverId);
    }

    @Test
    public void testDeleteById() {
        when(driverValidation.findIfExistsById(driverId)).thenReturn(driver);

        driverService.deleteById(driverId);

        assertTrue(driver.isDeleted()); 

        verify(driverValidation).findIfExistsById(driverId);
        verify(driverValidation).isDeleted(driverId);
    }

    @Test
    public void testDeleteByIdWithCar() {
        driver.setCar(new Car());

        when(driverValidation.findIfExistsById(driverId)).thenReturn(driver);

        driverService.deleteById(driverId);

        assertTrue(driver.isDeleted());

        verify(driverValidation).findIfExistsById(driverId);
        verify(driverValidation).isDeleted(driverId);
    }

    @Test
    public void testAssignPersonalCar() {
        
        Car car = new Car();
        car.setId(carId);

        when(driverValidation.findIfExistsById(driverId)).thenReturn(driver);
        when(carValidation.findIfExistsById(carId)).thenReturn(car);
        when(driverRepository.saveAndFlush(driver)).thenReturn(driver);
        when(driverMapper.toResponse(driver)).thenReturn(driverResponse);

        DriverResponse result = driverService.assignPersonalCar(driverId, carId);

        assertNotNull(result);
        assertEquals(driverResponse.name(), result.name());
        assertEquals(driverResponse.surname(), result.surname());
        assertEquals(driverResponse.email(), result.email());
        assertEquals(driverResponse.sex(), result.sex());
        assertEquals(driverResponse.carId(), result.carId());

        verify(driverValidation).findIfExistsById(driverId);
        verify(driverValidation).isDeleted(driverId);
        verify(carValidation).findIfExistsById(carId);
        verify(carValidation).isDeleted(carId);
        verify(carValidation).carIsAlreadyOccupied(carId);

    }

    @Test
    public void testAssignPersonalCarWithCarBefore() {

        driver.setCar(new Car());

        Car car = new Car();
        car.setId(carId);

        when(driverValidation.findIfExistsById(driverId)).thenReturn(driver);
        when(carValidation.findIfExistsById(carId)).thenReturn(car);
        when(driverRepository.saveAndFlush(driver)).thenReturn(driver);
        when(driverMapper.toResponse(driver)).thenReturn(driverResponse);

        DriverResponse result = driverService.assignPersonalCar(driverId, carId);

        assertNotNull(result);
        assertEquals(driverResponse.name(), result.name());
        assertEquals(driverResponse.surname(), result.surname());
        assertEquals(driverResponse.email(), result.email());
        assertEquals(driverResponse.sex(), result.sex());
        assertEquals(driverResponse.carId(), result.carId());

        verify(driverValidation).findIfExistsById(driverId);
        verify(driverValidation).isDeleted(driverId);
        verify(carValidation).findIfExistsById(carId);
        verify(carValidation).isDeleted(carId);
        verify(carValidation).carIsAlreadyOccupied(carId);

    }



}