package com.Harevich.passengerservice;
import com.Harevich.passengerservice.dto.PassengerRequest;
import com.Harevich.passengerservice.exceptions.PassengersDataRepeatException;
import com.Harevich.passengerservice.service.PassengerService;
import com.Harevich.passengerservice.util.mapper.PassengerMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class PassengerServiceUnitTest {

    private final PassengerService service;
    private final PassengerMapper passengerMapper;

    //<--------------- Registration ---------------->
    @Test
    void shouldCreatePassenger() {
        var request = TemplateTestData.createFirstPassengerRequest();
        var actual = service.create(request);
        Assertions.assertEquals(request,passengerMapper.toRequest(actual));
    }

    @Test
    void shouldThrowConflictWhileCreatingPassenger() {
        var test = TemplateTestData.createFirstPassengerRequest();
        service.create(test);
        var request = TemplateTestData.createFirstPassengerRequest();
        Assertions.assertThrows(PassengersDataRepeatException.class, () -> service.create(request));
    }

//    //<--------------- Edit ---------------->
    @Test
    void shouldEditPassenger() {
        var test = TemplateTestData.createFirstPassengerRequest();
        UUID id = service.create(test).id();
        var edit = new PassengerRequest(
                "Yura",
                "Harevich",
                "Nearsen@gmail.com",
                ""
        );
        var passenger = service.update(edit,id);
        Assertions.assertEquals(edit,passengerMapper.toRequest(passenger));
        Assertions.assertEquals(id,passenger.id());
    }

    @Test
    void shouldThrowConflictWhileEditingPassenger() {
        var test1 = TemplateTestData.createFirstPassengerRequest();
        service.create(test1);
        var test2 = TemplateTestData.createSecondPassengerRequest();
        var request = TemplateTestData.createEditPassengerRequestWithConflict();
        UUID id = service.create(test2).id();
        Assertions.assertThrows(PassengersDataRepeatException.class,() -> service.update(request,id));
    }
//    //<--------------- GetById ---------------->
    @Test
    void shouldFindPassengerById() {
        var test = TemplateTestData.createFirstPassengerRequest();
        UUID id = service.create(test).id();
        Assertions.assertEquals(test,passengerMapper.toRequest(service.getById(id)));
    }
    @Test
    void shouldThrowNotFoundWhineSearchingForPassenger() {
        Assertions.assertThrows(EntityNotFoundException.class,() -> service.getById(UUID.randomUUID()));
    }
//    //<--------------- DeleteById ---------------->
    @Test
    void shouldDeletePassengerById() {
        var test = TemplateTestData.createFirstPassengerRequest();
        UUID id = service.create(test).id();
        service.deleteById(id);
        Assertions.assertThrows(EntityNotFoundException.class,() -> service.getById(id));
    }
    @Test
    void shouldThrowNotFoundWhineDeletingPassenger() {
        Assertions.assertThrows(EntityNotFoundException.class,() -> service.deleteById(UUID.randomUUID()));
    }

}