package com.Harevich.passengerservice.util.mapper;

import com.Harevich.passengerservice.dto.PassengerRequest;
import com.Harevich.passengerservice.dto.PassengerResponse;
import com.Harevich.passengerservice.model.Passenger;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-24T16:05:56+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class PassengerMapperImpl implements PassengerMapper {

    @Override
    public PassengerResponse toResponse(Passenger passenger) {
        if ( passenger == null ) {
            return null;
        }

        UUID id = null;
        String name = null;
        String surname = null;
        String email = null;
        String number = null;

        id = passenger.getId();
        name = passenger.getName();
        surname = passenger.getSurname();
        email = passenger.getEmail();
        number = passenger.getNumber();

        PassengerResponse passengerResponse = new PassengerResponse( id, name, surname, email, number );

        return passengerResponse;
    }

    @Override
    public PassengerRequest toRequest(PassengerResponse passenger) {
        if ( passenger == null ) {
            return null;
        }

        String name = null;
        String surname = null;
        String email = null;
        String number = null;

        name = passenger.name();
        surname = passenger.surname();
        email = passenger.email();
        number = passenger.number();

        PassengerRequest passengerRequest = new PassengerRequest( name, surname, email, number );

        return passengerRequest;
    }

    @Override
    public Passenger toPassenger(PassengerRequest passengerRequest) {
        if ( passengerRequest == null ) {
            return null;
        }

        Passenger.PassengerBuilder passenger = Passenger.builder();

        passenger.name( passengerRequest.name() );
        passenger.surname( passengerRequest.surname() );
        passenger.email( passengerRequest.email() );
        passenger.number( passengerRequest.number() );

        return passenger.build();
    }

    @Override
    public void changePassengerByRequest(PassengerRequest passengerRequest, Passenger passenger) {
        if ( passengerRequest == null ) {
            return;
        }

        if ( passengerRequest.name() != null ) {
            passenger.setName( passengerRequest.name() );
        }
        if ( passengerRequest.surname() != null ) {
            passenger.setSurname( passengerRequest.surname() );
        }
        if ( passengerRequest.email() != null ) {
            passenger.setEmail( passengerRequest.email() );
        }
        if ( passengerRequest.number() != null ) {
            passenger.setNumber( passengerRequest.number() );
        }
    }
}
