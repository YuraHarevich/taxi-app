package com.Harevich.driverservice.controller.impl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.Harevich.driverservice.controller.ex.GlobalExceptionHandler;
import com.Harevich.driverservice.dto.request.DriverRequest;
import com.Harevich.driverservice.model.enumerations.Sex;
import com.Harevich.driverservice.service.DriverService;
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
public class DriverControllerUnitTest {

    @Mock
    private DriverService driverService;

    @InjectMocks
    private DriverController driverController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(driverController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateDriver_InvalidRequest() throws Exception {
        DriverRequest invalidRequest = new DriverRequest("", "123", "invalidEmail","asf", Sex.FEMALE.toString());

        mockMvc.perform(post("/api/v1/drivers/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateDriver_InvalidRequest() throws Exception {
        DriverRequest invalidRequest = new DriverRequest("", "123", "invalidEmail","asf", Sex.FEMALE.toString());
        UUID id = UUID.randomUUID();

        mockMvc.perform(patch("/api/v1/drivers/update?id=" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetDriverById_InvalidId() throws Exception {
        String invalidId = "123";

        mockMvc.perform(get("/api/v1/drivers?id=" + invalidId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteDriverById_InvalidId() throws Exception {
        String invalidId = "123";

        mockMvc.perform(delete("/api/v1/drivers?id=" + invalidId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAssignCarToDriver_InvalidId() throws Exception {
        String invalidDriverId = "123";
        String invalidCarId = "abc";

        mockMvc.perform(patch("/api/v1/drivers/changeCar?driver_id=" + invalidDriverId + "&car_id=" + invalidCarId))
                .andExpect(status().isBadRequest());
    }
}
