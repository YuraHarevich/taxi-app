package com.Harevich.rideservice.kafka.consumer;

import com.Harevich.rideservice.dto.request.DriverQueueRequest;
import com.Harevich.rideservice.dto.request.RideRequest;
import com.Harevich.rideservice.service.impl.QueueService;
import com.Harevich.rideservice.service.impl.RideServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {

    private final RideServiceImpl rideService;

    private final QueueService queueService;

    @Value("${spring.kafka.topic.order-queue}")
    private String orderTopic;

    private final KafkaTemplate<String, RideRequest> kafkaTemplate;
    @KafkaListener(topics = "order-topic",groupId = "order-group")
    public void consumeSupplyRequests(DriverQueueRequest driverQueueRequest) throws MessagingException {
        log.info("Consuming the message from topic {} for driver {}", orderTopic, driverQueueRequest.driverId());
        var rideRequestOptional = queueService.pickPassenger();
        var driverRequestOptional = queueService.pickDriver();

        if(rideRequestOptional.isPresent() && driverRequestOptional.isPresent()){
            rideService.createRide(rideRequestOptional.get(),driverRequestOptional.get().driverId());
        }
    }

}
