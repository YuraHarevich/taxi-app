package com.Harevich.passengerservice;
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
        var conflictPassengerWithTheSameData = TemplateTestData.createFirstPassengerRequest();
        service.create(conflictPassengerWithTheSameData);
        var request = TemplateTestData.createFirstPassengerRequest();
        Assertions.assertThrows(PassengersDataRepeatException.class, () -> service.create(request));
    }

//    //<--------------- Edit ---------------->
    @Test
    void shouldUpdatePassenger() {
        var passengerToUpdate = TemplateTestData.createFirstPassengerRequest();
        UUID passengerToUpdateId = service.create(passengerToUpdate).id();
        var editRequest = TemplateTestData.createUpdatePassengerRequest();
        var updatedPassenger = service.update(editRequest, passengerToUpdateId);

        Assertions.assertEquals(editRequest,passengerMapper.toRequest(updatedPassenger));
        Assertions.assertEquals(passengerToUpdateId,updatedPassenger.id());
    }

    @Test
    void shouldThrowConflictWhileEditingPassenger() {
        var firstPassengerReq = TemplateTestData.createFirstPassengerRequest();
        service.create(firstPassengerReq);
        var secondPassengerReq = TemplateTestData.createSecondPassengerRequest();
        UUID id = service.create(secondPassengerReq).id();
        var updateSecondPassengerWithRepeatedNumberOfTheFirstPassenger = TemplateTestData.createUpdatePassengerRequestWithConflict();

        Assertions.assertThrows(PassengersDataRepeatException.class,() -> service.update(updateSecondPassengerWithRepeatedNumberOfTheFirstPassenger,id));
    }
//    //<--------------- GetById ---------------->
    @Test
    void shouldFindPassengerById() {
        var passengerToFind = TemplateTestData.createFirstPassengerRequest();
        UUID id = service.create(passengerToFind).id();
        Assertions.assertEquals(passengerToFind,passengerMapper.toRequest(service.getById(id)));
    }
    @Test
    void shouldThrowNotFoundWhineSearchingForPassenger() {
        Assertions.assertThrows(EntityNotFoundException.class,() -> service.getById(UUID.randomUUID()));
    }
//    //<--------------- DeleteById ---------------->
    @Test
    void shouldDeletePassengerById() {
        var passengerToDelete = TemplateTestData.createFirstPassengerRequest();
        UUID id = service.create(passengerToDelete).id();
        service.deleteById(id);
        Assertions.assertThrows(EntityNotFoundException.class,() -> service.getById(id));
    }
    @Test
    void shouldThrowNotFoundWhineDeletingPassenger() {
        Assertions.assertThrows(EntityNotFoundException.class,() -> service.deleteById(UUID.randomUUID()));
    }

}