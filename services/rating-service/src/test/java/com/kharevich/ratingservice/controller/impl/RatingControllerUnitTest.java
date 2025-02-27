package com.kharevich.ratingservice.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kharevich.ratingservice.controller.exceptions.GlobalExceptionHandler;
import com.kharevich.ratingservice.dto.request.RatingRequest;
import com.kharevich.ratingservice.service.RatingService;
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

import static com.kharevich.ratingservice.model.enumerations.RatingPerson.DRIVER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RatingControllerUnitTest {

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

    private MockMvc mockMvc;
    private RatingRequest ratingRequest;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testEstimateTheRide_InvalidRequest() throws Exception {
        ratingRequest = new RatingRequest(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(),6, DRIVER,"so so");

        mockMvc.perform(post("/api/v1/ratings/estimation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllRatingsByDriverId_InvalidId() throws Exception {
        mockMvc.perform(get("/api/v1/ratings/driver/all")
                        .param("driverId", "123")
                        .param("pageNumber", "0")
                        .param("size", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllRatingsByPassengerId_InvalidId() throws Exception {
        mockMvc.perform(get("/api/v1/ratings/passenger/all")
                        .param("passengerId", "123")
                        .param("pageNumber", "0")
                        .param("size", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPassengerRating_InvalidId() throws Exception {
        mockMvc.perform(get("/api/v1/ratings/passenger")
                        .param("passengerId", "123"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetDriverRating_InvalidId() throws Exception {
        mockMvc.perform(get("/api/v1/ratings/driver")
                        .param("driverId", "123"))
                .andExpect(status().isBadRequest());
    }
}