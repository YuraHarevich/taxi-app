package com.Harevich.driverservice.controller.impl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.Harevich.driverservice.controller.ex.GlobalExceptionHandler;
import com.Harevich.driverservice.dto.request.CarRequest;
import com.Harevich.driverservice.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CarControllerUnitTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(carController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateNewCar_InvalidRequest() throws Exception {
        CarRequest invalidRequest = new CarRequest("", "123", "");

        mockMvc.perform(post("/api/v1/cars/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCar_InvalidRequest() throws Exception {
        CarRequest invalidRequest = new CarRequest("", "123", "");
        UUID id = UUID.randomUUID();

        mockMvc.perform(patch("/api/v1/cars/update?id=" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetCarByNumber_InvalidNumber() throws Exception {
        String invalidNumber = "123";

        mockMvc.perform(get("/api/v1/cars/number?number=" + invalidNumber))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllAvailableCars_InvalidPageNumber() throws Exception {
        int invalidPageNumber = -1;
        int size = 10;

        mockMvc.perform(get("/api/v1/cars/available?page_number=" + invalidPageNumber + "&size=" + size))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllAvailableCars_InvalidSize() throws Exception {
        int invalidpageNumber = -1;
        int size = 10;

        mockMvc.perform(get("/api/v1/cars/available?page_number=" + invalidpageNumber + "&size=" + size))
                .andExpect(status().isBadRequest());
    }
}