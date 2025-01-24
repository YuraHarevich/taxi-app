package com.Harevich.passenger_service;
import com.Harevich.passenger_service.dto.PassengerRequest;
import com.Harevich.passenger_service.exceptions.UniqueException;
import com.Harevich.passenger_service.service.PassengerService;
import com.Harevich.passenger_service.service.impl.PassengerServiceImpl;
import com.Harevich.passenger_service.util.mapper.PassengerMapper;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class PassengerServiceUnitTest {

    private final PassengerService service;
    private final PassengerMapper passengerMapper;
    @Autowired
    PassengerServiceUnitTest(PassengerServiceImpl service, PassengerMapper passengerMapper) {
        this.service = service;
        this.passengerMapper = passengerMapper;
    }

    //<--------------- Registration ---------------->
    @Test
    void shouldCreatePassenger() {
        var request = new PassengerRequest(
                "Arsen",
                "Hydnitsky",
                "kryptoBober@gmail.com",
                "+375447555799"
        );
        var actual = service.registrate(request);
        Assertions.assertEquals(request,passengerMapper.toRequest(actual));
    }

    @Test
    void shouldThrowConflictWhileCreatingPassenger() {
        var test = new PassengerRequest(
                "Arsen",
                "Hydnitsky",
                "kryptoBober@gmail.com",
                "+375447555799"
        );
        service.registrate(test);
        var request = new PassengerRequest(
                "Arsen",
                "Hydnitsky",
                "kryptoBober@gmail.com",
                "+375447555799"
        );
        Assertions.assertThrows(UniqueException.class, () -> service.registrate(request));
    }

//    //<--------------- Edit ---------------->
    @Test
    void shouldEditPassenger() {
        var test = new PassengerRequest(
                "Arsen",
                "Hydnitsky",
                "kryptoBober@gmail.com",
                "+375447555799"
        );
        UUID id = service.registrate(test).id();
        var edit = new PassengerRequest(
                "Yura",
                "Harevich",
                "NekryptoBober@gmail.com",
                "+375447555799"
        );
        var passenger = service.edit(edit,id);
        Assertions.assertEquals(edit,passengerMapper.toRequest(passenger));
        Assertions.assertEquals(id,passenger.id());
    }

    @Test
    void shouldThrowConflictWhileEditingPassenger() {
        var test1 = new PassengerRequest(
                "Arsen",
                "Hydnitsky",
                "kryptoBober@gmail.com",
                "+375447555799"
        );
        service.registrate(test1).id();
        var test2 = new PassengerRequest(
                "NeArsen",
                "NeHydnitsky",
                "NekryptoBober@gmail.com",
                "+375447555798"
        );
        var request = new PassengerRequest(
                "TochnoNeArsen",
                "TochnoNeHydnitsky",
                "TochnoNekryptoBober@gmail.com",
                "+375447555799" //а вот номерок точно повторяется
        );
        UUID id = service.registrate(test2).id();
        Assertions.assertThrows(DataIntegrityViolationException.class,() -> service.edit(request,id));
    }
//    //<--------------- GetById ---------------->
    @Test
    void shouldFindPassengerById() {
        var test = new PassengerRequest(
                "Arsen",
                "Hydnitsky",
                "kryptoBober@gmail.com",
                "+375447555799"
        );
        UUID id = service.registrate(test).id();
        Assertions.assertEquals(test,passengerMapper.toRequest(service.getById(id)));
    }
    @Test
    void shouldThrowNotFoundWhineSearchingForPassenger() {
        Assertions.assertThrows(EntityNotFoundException.class,() -> service.getById(UUID.randomUUID()));
    }
//    //<--------------- DeleteById ---------------->
    @Test
    void shouldDeletePassengerById() {
        var test = new PassengerRequest(
                "Arsen",
                "Hydnitsky",
                "kryptoBober@gmail.com",
                "+375447555799"
        );
        UUID id = service.registrate(test).id();
        service.deleteById(id);
        Assertions.assertThrows(EntityNotFoundException.class,() -> service.getById(id));
    }
    @Test
    void shouldThrowNotFoundWhineDeletingForPassenger() {
        Assertions.assertThrows(EntityNotFoundException.class,() -> service.deleteById(UUID.randomUUID()));
    }

}