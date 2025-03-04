package com.Harevich.driverservice.controller.impl;

import com.Harevich.driverservice.controller.ex.GlobalExceptionHandler;
import com.Harevich.driverservice.dto.request.DriverRequest;
import com.Harevich.driverservice.dto.response.DriverResponse;
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

import static com.Harevich.driverservice.model.enumerations.Sex.MALE;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class DriverControllerUnitTest {

    @Mock
    private DriverService driverService;

    @InjectMocks
    private DriverController driverController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(driverController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        baseUrl = "/api/v1/drivers";
    }

    @Test
    public void testCreateDriver_ValidRequest() throws Exception {
        DriverRequest validRequest = new DriverRequest("John","Doe", "john.doe@example.com", "+375447525709",MALE.toString());
        DriverResponse response = new DriverResponse(UUID.randomUUID(), "John","Doe", "john.doe@example.com",MALE,UUID.randomUUID());

        when(driverService.createNewDriver(validRequest)).thenReturn(response);

        mockMvc.perform(post(baseUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testCreateDriver_InvalidRequest() throws Exception {
        DriverRequest invalidRequest = new DriverRequest("John","Doe", "mple.com", "+37547525709",MALE.toString());

        mockMvc.perform(post(baseUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateDriver_ValidRequest() throws Exception {
        UUID id = UUID.randomUUID();
        DriverRequest validRequest = new DriverRequest("John","Doe", "john.doe@example.com", "+375447525709",MALE.toString());
        DriverResponse response = new DriverResponse(id, "John","Doe", "john.doe@example.com",MALE,UUID.randomUUID());

        when(driverService.updateDriver(validRequest, id)).thenReturn(response);

        mockMvc.perform(patch(baseUrl + "/update?id=" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testUpdateDriver_InvalidRequest() throws Exception {
        UUID id = UUID.randomUUID();
        DriverRequest invalidRequest = new DriverRequest("John","Doe", "mple.com", "+37547525709",MALE.toString());

        mockMvc.perform(patch(baseUrl + "/update?id=" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetDriverById_ValidId() throws Exception {
        UUID id = UUID.randomUUID();
        DriverResponse response = new DriverResponse(id, "John","Doe", "john.doe@example.com",MALE,UUID.randomUUID());

        when(driverService.getById(id)).thenReturn(response);

        mockMvc.perform(get(baseUrl + "?id=" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testGetDriverById_InvalidId() throws Exception {
        String invalidId = "invalid-uuid";

        mockMvc.perform(get(baseUrl + "?id=" + invalidId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteDriverById_ValidId() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete(baseUrl + "?id=" + id))
                .andExpect(status().isOk());

        verify(driverService).deleteById(id);
    }

    @Test
    public void testDeleteDriverById_InvalidId() throws Exception {
        String invalidId = "invalid-uuid";

        mockMvc.perform(delete(baseUrl + "?id=" + invalidId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAssignPersonalCar_ValidIds() throws Exception {
        UUID driverId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();
        DriverResponse response = new DriverResponse(driverId, "John","Doe", "john.doe@example.com",MALE,carId);

        when(driverService.assignPersonalCar(driverId, carId)).thenReturn(response);

        mockMvc.perform(patch(baseUrl + "/changeCar?driver_id=" + driverId + "&car_id=" + carId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(driverId.toString()))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.carId").value(carId.toString()));
    }

    @Test
    public void testAssignPersonalCar_InvalidIds() throws Exception {
        String invalidDriverId = "invalid-uuid";
        String invalidCarId = "invalid-uuid";

        mockMvc.perform(patch(baseUrl + "/changeCar?driver_id=" + invalidDriverId + "&car_id=" + invalidCarId))
                .andExpect(status().isBadRequest());
    }
}