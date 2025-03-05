package com.Harevich.driverservice.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.Harevich.driverservice.dto.request.CarRequest;
import com.Harevich.driverservice.dto.response.CarResponse;
import com.Harevich.driverservice.dto.response.PageableResponse;
import com.Harevich.driverservice.model.Car;
import com.Harevich.driverservice.model.Driver;
import com.Harevich.driverservice.repository.CarRepository;
import com.Harevich.driverservice.repository.DriverRepository;
import com.Harevich.driverservice.service.impl.CarServiceImpl;
import com.Harevich.driverservice.util.mapper.CarMapper;
import com.Harevich.driverservice.util.mapper.PageMapper;
import com.Harevich.driverservice.util.validation.car.CarValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CarServiceUnitTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private CarMapper carMapper;

    @Mock
    private CarValidation carValidation;

    @Mock
    private PageMapper pageMapper;

    @InjectMocks
    private CarServiceImpl carService;

    private CarRequest carRequest;
    private Car car;
    private CarResponse carResponse;
    private UUID carId;
    private UUID driverId;

    @BeforeEach
    public void setUp() {
        carId = UUID.randomUUID();
        driverId = UUID.randomUUID();

        carRequest = new CarRequest("red", "7777 BB-7", "BMW");
        car = new Car();
        car.setId(carId);
        car.setColor("red");
        car.setNumber("7777 BB-7");
        car.setBrand("BMW");

        carResponse = new CarResponse(carId, "red", "7777 BB-7", "BMW", null);
    }

    @Test
    public void testCreateNewCar() {
        when(carMapper.toCar(carRequest)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(car);
        when(carMapper.toResponse(car)).thenReturn(carResponse);

        CarResponse result = carService.createNewCar(carRequest);

        assertNotNull(result);
        assertEquals(carResponse.color(), result.color());
        assertEquals(carResponse.number(), result.number());
        assertEquals(carResponse.brand(), result.brand());

        verify(carValidation).alreadyExistsByNumber(carRequest.number());
        verify(carRepository).save(car);
        verify(carMapper).toResponse(car);
    }

    @Test
    public void testUpdateCar() {
        when(carValidation.findIfExistsById(carId)).thenReturn(car);
        when(carRepository.saveAndFlush(car)).thenReturn(car);
        when(carMapper.toResponse(car)).thenReturn(carResponse);

        CarResponse result = carService.updateCar(carRequest, carId);

        assertNotNull(result);
        assertEquals(carResponse.color(), result.color());
        assertEquals(carResponse.number(), result.number());
        assertEquals(carResponse.brand(), result.brand());

        verify(carValidation).findIfExistsById(carId);
        verify(carValidation).alreadyExistsByNumber(carRequest.number());
        verify(carValidation).isDeleted(carId);
        verify(carMapper).changeCarByRequest(carRequest, car);
        verify(carRepository).saveAndFlush(car);
    }

    @Test
    public void testGetCarById() {
        when(carValidation.findIfExistsById(carId)).thenReturn(car);
        when(carMapper.toResponse(car)).thenReturn(carResponse);

        CarResponse result = carService.getCarById(carId);

        assertNotNull(result);
        assertEquals(carResponse.color(), result.color());
        assertEquals(carResponse.number(), result.number());
        assertEquals(carResponse.brand(), result.brand());

        verify(carValidation).findIfExistsById(carId);
        verify(carValidation).isDeleted(carId);
        verify(carMapper).toResponse(car);
    }

    @Test
    public void testGetCarByNumber() {
        String number = "7777 BB-7";
        when(carValidation.findIfExistsByNumber(number)).thenReturn(car);
        when(carMapper.toResponse(car)).thenReturn(carResponse);

        CarResponse result = carService.getCarByNumber(number);

        assertNotNull(result);
        assertEquals(carResponse.color(), result.color());
        assertEquals(carResponse.number(), result.number());
        assertEquals(carResponse.brand(), result.brand());

        verify(carValidation).findIfExistsByNumber(number);
        verify(carValidation).isDeleted(carId);
        verify(carMapper).toResponse(car);
    }

    @Test
    public void testDeleteCarById_DriverIsNull() {
        when(carValidation.findIfExistsById(carId)).thenReturn(car);

        carService.deleteCarById(carId);

        assertTrue(car.isDeleted());
        assertNull(car.getDriver());

        verify(carValidation).findIfExistsById(carId);
        verify(carValidation).isDeleted(carId);
        verify(carRepository).save(car);
    }

    @Test
    public void testDeleteCarById_DriverIsNotNull() {
        Driver driver = new Driver();
        driver.setId(driverId);
        car.setDriver(driver);

        when(carValidation.findIfExistsById(carId)).thenReturn(car);

        carService.deleteCarById(carId);

        assertTrue(car.isDeleted());
        assertNull(car.getDriver());
        assertNull(driver.getCar());

        verify(carValidation).findIfExistsById(carId);
        verify(carValidation).isDeleted(carId);
        verify(carRepository).save(car);
    }

    @Test
    public void testGetAllAvailableCars() {
        int pageNumber = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(pageNumber, size);
        Page<CarResponse> carPage = mock(Page.class);
        when(carRepository.findByDriverIsNullAndDeletedFalse(pageRequest)).thenReturn(carPage);
        when(pageMapper.toResponse(carPage)).thenReturn(new PageableResponse<>(1,1,1,1, Collections.emptyList()));

        PageableResponse<CarResponse> result = carService.getAllAvailableCars(pageNumber, size);

        assertNotNull(result);

        verify(carRepository).findByDriverIsNullAndDeletedFalse(pageRequest);
        verify(pageMapper).toResponse(carPage);
    }

}