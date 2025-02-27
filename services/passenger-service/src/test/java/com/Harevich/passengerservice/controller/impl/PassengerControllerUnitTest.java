package com.Harevich.passengerservice.controller.impl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.Harevich.passengerservice.controller.GlobalExceptionHandler;
import com.Harevich.passengerservice.dto.PassengerRequest;
import com.Harevich.passengerservice.service.PassengerService;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PassengerControllerUnitTest {

    @Mock
    private PassengerService passengerService;

    @InjectMocks
    private PassengerControllerImpl passengerController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(passengerController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreatePassenger_InvalidRequest() throws Exception {
        PassengerRequest invalidRequest = new PassengerRequest("", "123", "invalidEmail","asf");

        mockMvc.perform(post("/api/v1/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdatePassenger_InvalidRequest() throws Exception {
        PassengerRequest invalidRequest = new PassengerRequest("", "123", "invalidEmail","asf");
        UUID id = UUID.randomUUID();

        mockMvc.perform(patch("/api/v1/passengers?id=" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPassengerById_InvalidId() throws Exception {
        String invalidId = "123";

        mockMvc.perform(get("/api/v1/passengers?id=" + invalidId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeletePassengerById_InvalidId() throws Exception {
        String invalidId = "123";

        mockMvc.perform(delete("/api/v1/passengers?id=" + invalidId))
                .andExpect(status().isBadRequest());
    }
}
