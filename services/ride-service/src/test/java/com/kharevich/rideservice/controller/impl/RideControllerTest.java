package com.kharevich.rideservice.controller.impl;

import com.kharevich.rideservice.dto.request.RideRequest;
import com.kharevich.rideservice.dto.response.PageableResponse;
import com.kharevich.rideservice.dto.response.RideResponse;
import com.kharevich.rideservice.model.enumerations.RideStatus;
import com.kharevich.rideservice.service.RideService;
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

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.UUID;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RideControllerTest {

    @Mock
    private RideService rideService;

    @InjectMocks
    private RideController rideController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private RideRequest request;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(rideController).build();
        objectMapper = new ObjectMapper();
        request = new RideRequest("From", "To",UUID.randomUUID());
    }

    @Test
    void applyForDriver_positive() throws Exception {
        UUID driverId = UUID.randomUUID();

        mockMvc.perform(post("/api/v1/rides/order/driver")
                        .param("driver_id", driverId.toString()))
                .andExpect(status().isOk());

        verify(rideService).applyForDriver(driverId);
    }

    @Test
    void applyForDriver_negative_missingDriverId() throws Exception {
        mockMvc.perform(post("/api/v1/rides/order/driver"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createOrder_positive() throws Exception {
        mockMvc.perform(post("/api/v1/rides/order/passenger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(rideService).sendRideRequest(request);
    }

    @Test
    void createOrder_negative_invalidRequest() throws Exception {
        String invalidJson = "{ \"from\": \"From\", \"to\": \"To\", \"passengerId\": \"not-a-uuid\" }";

        mockMvc.perform(post("/api/v1/rides/order/passenger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRide_positive() throws Exception {
        UUID driverId = UUID.randomUUID();

        RideResponse response = new RideResponse(UUID.randomUUID(), "From", "To", BigDecimal.ONE, request.passengerId(), driverId, RideStatus.CREATED, Duration.ZERO);

        when(rideService.createRide(request, driverId)).thenReturn(response);

        mockMvc.perform(post("/api/v1/rides")
                        .param("driver_id", driverId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.driverId").value(driverId.toString()))
                .andExpect(jsonPath("$.from").value("From"))
                .andExpect(jsonPath("$.to").value("To"));
    }

    @Test
    void createRide_negative_missingDriverId() throws Exception {
        mockMvc.perform(post("/api/v1/rides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateRide_positive() throws Exception {
        UUID rideId = UUID.randomUUID();
        RideResponse response = new RideResponse(rideId, "Updated From", "Updated To", BigDecimal.ONE, request.passengerId(), UUID.randomUUID(), RideStatus.CREATED, Duration.ZERO);
        request = new RideRequest("Updated From", "Updated To",UUID.randomUUID());

        when(rideService.updateRide(request, rideId)).thenReturn(response);

        mockMvc.perform(patch("/api/v1/rides")
                        .param("id", rideId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(rideId.toString()))
                .andExpect(jsonPath("$.from").value("Updated From"))
                .andExpect(jsonPath("$.to").value("Updated To"));
    }

    @Test
    void updateRide_negative_invalidRequest() throws Exception {
        UUID rideId = UUID.randomUUID();
        String invalidJson = "{ \"from\": \"From\", \"to\": \"To\", \"passengerId\": \"not-a-uuid\" }";


        mockMvc.perform(patch("/api/v1/rides")
                        .param("id", rideId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void changeRideStatus_positive() throws Exception {
        UUID rideId = UUID.randomUUID();

        RideResponse response = new RideResponse(rideId, "From", "To", BigDecimal.ONE, request.passengerId(), UUID.randomUUID(), RideStatus.ACCEPTED, Duration.ZERO);

        when(rideService.changeRideStatus(rideId)).thenReturn(response);

        mockMvc.perform(patch("/api/v1/rides/change-status")
                        .param("id", rideId.toString()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(rideId.toString()));
    }

    @Test
    void changeRideStatus_negative_missingId() throws Exception {
        mockMvc.perform(patch("/api/v1/rides/change-status"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getRideById_positive() throws Exception {
        UUID rideId = UUID.randomUUID();
        RideResponse response = new RideResponse(rideId, "From", "To", BigDecimal.ONE, request.passengerId(), UUID.randomUUID(), RideStatus.CREATED, Duration.ZERO);

        when(rideService.getRideById(rideId)).thenReturn(response);

        mockMvc.perform(get("/api/v1/rides")
                        .param("id", rideId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rideId.toString()));
    }

    @Test
    void getRideById_negative_missingId() throws Exception {
        mockMvc.perform(get("/api/v1/rides"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllRides_positive() throws Exception {
        PageableResponse<RideResponse> response = new PageableResponse<>(1, 1, 0, 10, List.of(
               new RideResponse(UUID.randomUUID(), "From", "To", BigDecimal.ONE, request.passengerId(), UUID.randomUUID(), RideStatus.CREATED, Duration.ZERO)
        ));

        when(rideService.getAllRides(0, 10)).thenReturn(response);

        mockMvc.perform(get("/api/v1/rides/all")
                        .param("pageNumber", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.content[0].from").value("From"));
    }

    @Test
    void getAllRidesWithInvalidSize() throws Exception {
        // Подготовка данных
        PageableResponse<RideResponse> response = new PageableResponse<>(1, 1, 0, 50, List.of(
                new RideResponse(UUID.randomUUID(), "From", "To", BigDecimal.ONE, request.passengerId(), UUID.randomUUID(), RideStatus.CREATED, Duration.ZERO)
        ));

        // Заглушка для сервиса с size = 50 (так как контроллер ограничивает size до 50)
        when(rideService.getAllRides(0, 50)).thenReturn(response);

        // Вызов метода с size = 10000 (но контроллер ограничит его до 50)
        mockMvc.perform(get("/api/v1/rides/all")
                        .param("pageNumber", "0")
                        .param("size", "10000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.content[0].from").value("From"));

        // Проверка, что сервис был вызван с size = 50
        verify(rideService).getAllRides(0, 50);
    }

    @Test
    void getAllRides_negative_invalidPageNumber() throws Exception {
        mockMvc.perform(get("/api/v1/rides/all")
                        .param("pageNumber", "-1") // Невалидное значение
                        .param("size", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllRidesByPassengerId_positive() throws Exception {
        UUID passengerId = UUID.randomUUID();
        PageableResponse<RideResponse> response = new PageableResponse<>(1, 1, 0, 10, List.of(
                new RideResponse(UUID.randomUUID(), "From", "To", BigDecimal.ONE, passengerId, passengerId, RideStatus.CREATED, Duration.ZERO)
        ));

        when(rideService.getAllRidesByPassengerId(passengerId, 0, 10)).thenReturn(response);

        mockMvc.perform(get("/api/v1/rides/all/passenger")
                        .param("passenger_id", passengerId.toString())
                        .param("pageNumber", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].passengerId").value(passengerId.toString()));
    }

    @Test
    void getAllRidesByPassengerIdInvalidSize() throws Exception {
        UUID passengerId = UUID.randomUUID();
        PageableResponse<RideResponse> response = new PageableResponse<>(1, 1, 0, 10, List.of(
                new RideResponse(UUID.randomUUID(), "From", "To", BigDecimal.ONE, passengerId, passengerId, RideStatus.CREATED, Duration.ZERO)
        ));

        when(rideService.getAllRidesByPassengerId(passengerId, 0, 50)).thenReturn(response);

        mockMvc.perform(get("/api/v1/rides/all/passenger")
                        .param("passenger_id", passengerId.toString())
                        .param("pageNumber", "0")
                        .param("size", "1000000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].passengerId").value(passengerId.toString()));
    }

    @Test
    void getAllRidesByPassengerId_negative_missingPassengerId() throws Exception {
        mockMvc.perform(get("/api/v1/rides/all/passenger")
                        .param("pageNumber", "0")
                        .param("size", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllRidesByDriverId_positive() throws Exception {
        UUID driverId = UUID.randomUUID();
        PageableResponse<RideResponse> response = new PageableResponse<>(1, 1, 0, 10, List.of(
                new RideResponse(UUID.randomUUID(), "From", "To", BigDecimal.ONE, request.passengerId(), driverId, RideStatus.CREATED, Duration.ZERO)
        ));

        when(rideService.getAllRidesByDriverId(driverId, 0, 10)).thenReturn(response);

        mockMvc.perform(get("/api/v1/rides/all/driver")
                        .param("driver_id", driverId.toString())
                        .param("pageNumber", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].driverId").value(driverId.toString()));
    }

    @Test
    void getAllRidesByDriverId_negative_missingDriverId() throws Exception {
        mockMvc.perform(get("/api/v1/rides/all/driver")
                        .param("pageNumber", "0")
                        .param("size", "10"))
                .andExpect(status().isBadRequest());
    }

}