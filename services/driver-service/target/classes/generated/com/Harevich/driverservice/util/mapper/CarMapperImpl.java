package com.Harevich.driverservice.util.mapper;

import com.Harevich.driverservice.dto.request.CarRequest;
import com.Harevich.driverservice.dto.response.CarResponse;
import com.Harevich.driverservice.model.Car;
import com.Harevich.driverservice.model.Driver;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T22:48:34+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class CarMapperImpl implements CarMapper {

    @Override
    public CarResponse toResponse(Car car) {
        if ( car == null ) {
            return null;
        }

        UUID driverId = null;
        UUID id = null;
        String color = null;
        String number = null;
        String brand = null;

        driverId = carDriverId( car );
        id = car.getId();
        color = car.getColor();
        number = car.getNumber();
        brand = car.getBrand();

        CarResponse carResponse = new CarResponse( id, color, number, brand, driverId );

        return carResponse;
    }

    @Override
    public CarRequest toRequest(CarResponse carResponse) {
        if ( carResponse == null ) {
            return null;
        }

        String color = null;
        String number = null;
        String brand = null;

        color = carResponse.color();
        number = carResponse.number();
        brand = carResponse.brand();

        CarRequest carRequest = new CarRequest( color, number, brand );

        return carRequest;
    }

    @Override
    public Car toCar(CarRequest carRequest) {
        if ( carRequest == null ) {
            return null;
        }

        Car.CarBuilder car = Car.builder();

        car.color( carRequest.color() );
        car.number( carRequest.number() );
        car.brand( carRequest.brand() );

        return car.build();
    }

    @Override
    public void changeCarByRequest(CarRequest carRequest, Car car) {
        if ( carRequest == null ) {
            return;
        }

        if ( carRequest.color() != null ) {
            car.setColor( carRequest.color() );
        }
        if ( carRequest.number() != null ) {
            car.setNumber( carRequest.number() );
        }
        if ( carRequest.brand() != null ) {
            car.setBrand( carRequest.brand() );
        }
    }

    private UUID carDriverId(Car car) {
        Driver driver = car.getDriver();
        if ( driver == null ) {
            return null;
        }
        return driver.getId();
    }
}
