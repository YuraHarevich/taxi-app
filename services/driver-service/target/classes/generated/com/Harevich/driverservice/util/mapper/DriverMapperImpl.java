package com.Harevich.driverservice.util.mapper;

import com.Harevich.driverservice.dto.request.DriverRequest;
import com.Harevich.driverservice.dto.response.DriverResponse;
import com.Harevich.driverservice.model.Car;
import com.Harevich.driverservice.model.Driver;
import com.Harevich.driverservice.model.enumerations.Sex;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T22:48:34+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class DriverMapperImpl implements DriverMapper {

    @Override
    public DriverResponse toResponse(Driver driver) {
        if ( driver == null ) {
            return null;
        }

        UUID carId = null;
        UUID id = null;
        String name = null;
        String surname = null;
        String email = null;
        Sex sex = null;

        carId = driverCarId( driver );
        id = driver.getId();
        name = driver.getName();
        surname = driver.getSurname();
        email = driver.getEmail();
        sex = driver.getSex();

        DriverResponse driverResponse = new DriverResponse( id, name, surname, email, sex, carId );

        return driverResponse;
    }

    @Override
    public Driver toDriver(DriverRequest driverRequest) {
        if ( driverRequest == null ) {
            return null;
        }

        Driver.DriverBuilder driver = Driver.builder();

        driver.name( driverRequest.name() );
        driver.surname( driverRequest.surname() );
        driver.number( driverRequest.number() );
        driver.email( driverRequest.email() );
        if ( driverRequest.sex() != null ) {
            driver.sex( Enum.valueOf( Sex.class, driverRequest.sex() ) );
        }

        return driver.build();
    }

    @Override
    public DriverRequest toRequest(DriverResponse driverResponse) {
        if ( driverResponse == null ) {
            return null;
        }

        String name = null;
        String surname = null;
        String email = null;
        String sex = null;

        name = driverResponse.name();
        surname = driverResponse.surname();
        email = driverResponse.email();
        if ( driverResponse.sex() != null ) {
            sex = driverResponse.sex().name();
        }

        String number = null;

        DriverRequest driverRequest = new DriverRequest( name, surname, email, number, sex );

        return driverRequest;
    }

    @Override
    public void changeDriverByRequest(DriverRequest driverRequest, Driver driver) {
        if ( driverRequest == null ) {
            return;
        }

        if ( driverRequest.name() != null ) {
            driver.setName( driverRequest.name() );
        }
        if ( driverRequest.surname() != null ) {
            driver.setSurname( driverRequest.surname() );
        }
        if ( driverRequest.number() != null ) {
            driver.setNumber( driverRequest.number() );
        }
        if ( driverRequest.email() != null ) {
            driver.setEmail( driverRequest.email() );
        }
        if ( driverRequest.sex() != null ) {
            driver.setSex( Enum.valueOf( Sex.class, driverRequest.sex() ) );
        }
    }

    private UUID driverCarId(Driver driver) {
        Car car = driver.getCar();
        if ( car == null ) {
            return null;
        }
        return car.getId();
    }
}
