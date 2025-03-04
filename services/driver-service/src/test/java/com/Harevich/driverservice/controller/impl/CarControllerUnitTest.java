package com.Harevich.driverservice.controller.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.Harevich.driverservice.controller.ex.GlobalExceptionHandler;
import com.Harevich.driverservice.dto.request.CarRequest;
import com.Harevich.driverservice.dto.response.CarResponse;
import com.Harevich.driverservice.dto.response.PageableResponse;
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

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CarControllerUnitTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(carController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        baseUrl = "/api/v1/cars";
    }

    @Test
    public void testCreateNewCar_InvalidRequest() throws Exception {
        CarRequest invalidRequest = new CarRequest("", "123", "");

        mockMvc.perform(post(baseUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCar_InvalidRequest() throws Exception {
        CarRequest invalidRequest = new CarRequest("", "123", "");
        UUID id = UUID.randomUUID();

        mockMvc.perform(patch(baseUrl + "/update?id=" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateNewCar_ValidRequest() throws Exception {
        CarRequest validRequest = new CarRequest("Toyota Camry", "7777 BB-7", "Sedan");

        mockMvc.perform(post(baseUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateCar_ValidRequest() throws Exception {
        CarRequest validRequest = new CarRequest("Toyota Camry", "7777 BB-7", "Sedan");
        UUID id = UUID.randomUUID();

        mockMvc.perform(patch(baseUrl + "/update?id=" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testGetCarById_ValidId() throws Exception {
        UUID validId = UUID.randomUUID();
        CarResponse carResponse =  new CarResponse(validId, "red","7777 BB-7", "Toyota", UUID.randomUUID());

        when(carService.getCarById(validId)).thenReturn(carResponse);

        mockMvc.perform(get(baseUrl + "/id?id=" + validId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(validId.toString()))
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.number").value("7777 BB-7"))
                .andExpect(jsonPath("$.color").value("red"));
    }

    @Test
    public void testGetCarById_InvalidId() throws Exception {
        String invalidId = "invalid-uuid";

        mockMvc.perform(get(baseUrl + "/id?id=" + invalidId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteCarById_ValidId() throws Exception {
        UUID validId = UUID.randomUUID();

        mockMvc.perform(delete(baseUrl + "?id=" + validId))
                .andExpect(status().isOk());

        verify(carService).deleteCarById(validId);
    }

    @Test
    public void testDeleteCarById_InvalidId() throws Exception {
        String invalidId = "invalid-uuid";

        mockMvc.perform(delete(baseUrl + "?id=" + invalidId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllAvailableCars_ValidPageNumberAndSize() throws Exception {
        int validPageNumber = 0;
        int validSize = 10;
        PageableResponse<CarResponse> response = new PageableResponse<>(1, 1, validPageNumber, validSize, List.of(
                new CarResponse(UUID.randomUUID(), "red","7777 BB-7", "Toyota", UUID.randomUUID())
        ));

        when(carService.getAllAvailableCars(validPageNumber, validSize)).thenReturn(response);

        mockMvc.perform(get(baseUrl + "/available?page_number=" + validPageNumber + "&size=" + validSize))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.content[0].brand").value("Toyota"));
    }

    @Test
    public void testGetAllAvailableCars_InvalidPageNumber() throws Exception {
        int invalidPageNumber = -1;
        int size = 10;

        mockMvc.perform(get(baseUrl + "/available?page_number=" + invalidPageNumber + "&size=" + size))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllAvailableCars_InvalidSize() throws Exception {
        int pageNumber = -1;
        int invalidSize = -1;

        mockMvc.perform(get(baseUrl + "/available?page_number=" + pageNumber + "&size=" + invalidSize))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetCarByNumber_ValidNumber() throws Exception {
        String validNumber = "7777 BB-7";
        CarResponse carResponse = new CarResponse(UUID.randomUUID(), "red","7777 BB-7", "Toyota", UUID.randomUUID());

        when(carService.getCarByNumber(validNumber)).thenReturn(carResponse);

        mockMvc.perform(get(baseUrl + "/number?number=" + validNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value(validNumber));
    }

    @Test
    public void testGetCarByNumber_InvalidNumber() throws Exception {
        String invalidNumber = "123";

        mockMvc.perform(get(baseUrl + "/number?number=" + invalidNumber))
                .andExpect(status().isBadRequest());
    }

}