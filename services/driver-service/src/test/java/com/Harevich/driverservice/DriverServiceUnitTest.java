package com.Harevich.driverservice;

import com.Harevich.driverservice.dto.CarRequest;
import com.Harevich.driverservice.dto.CarResponse;
import com.Harevich.driverservice.dto.DriverRequest;
import com.Harevich.driverservice.dto.DriverResponse;
import com.Harevich.driverservice.mapper.DriverMapper;
import com.Harevich.driverservice.service.DriverService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DriverServiceUnitTest {
	private final DriverService service;
	@Autowired
    DriverServiceUnitTest(DriverService service) {
        this.service = service;
    }
	@Test
	void shouldEditDriver() {
		Long id = 5l;
		Float rate = service.getById(id).getRate();
		Long car_id = service.getById(id).getCar().getId();
		var expected = new DriverResponse(
				id,
				"Arsen",
				"Hydnitsky",
				"kryptoBober@gmail.com",
				rate,
				new CarResponse(
						car_id,
						"yellow",
						"7192 RK-9",
						"Porshe"
				)
		);

		Assertions.assertEquals(expected, DriverMapper.toDriverResponse(service.edit(
				new DriverRequest(
						"Arsen",
						"Hydnitsky",
						"kryptoBober@gmail.com",
						new CarRequest(
								"yellow",
								"7192 RK-9",
								"Porshe"
						)),id)));
	}

	@Test
	void shouldFindDriver() {
		Long id = 5l;
		var expected = new DriverResponse(
				id,
				"Michael",
				"Brown",
				"michael.brown@example.com",
				4.6f,
				new CarResponse(
						5l,
						"White",
						"5678 EF-3",
						"Audi"
				)
		);
		Assertions.assertEquals(expected,DriverMapper.toDriverResponse(service.getById(id)));
	}
	@Test
	void shouldRegistrateDriver() {
		Long id = 5l;
		var request = new DriverRequest(
				"Maksim",
				"Komissarov",
				"Komissarov@gmail.com",
				new CarRequest(
						"Pink",
						"6969 GG-7",
						"Brabus"
				));
		var expected = new DriverResponse(
				service.getMaxId()+1,
				"Maksim",
				"Komissarov",
				"Komissarov@gmail.com",
				5f,
				new CarResponse(
						service.getMaxCarId(),
						"Pink",
						"6969 GG-7",
						"Brabus"
				)
		);

		Assertions.assertEquals(expected,DriverMapper.toDriverResponse(service.registrate(request)));
	}
}
