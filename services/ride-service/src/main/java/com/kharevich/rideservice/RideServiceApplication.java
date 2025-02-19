package com.kharevich.rideservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RideServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(RideServiceApplication.class, args);
	}
}
