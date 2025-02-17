package com.Harevich.rideservice.kafka.consumer;

import com.Harevich.rideservice.dto.request.RideRequest;
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

    @Value("${spring.kafka.topic.order-queue}")
    private String orderTopic;

    private final KafkaTemplate<String, RideRequest> kafkaTemplate;
    @KafkaListener(topics = "order-topic",groupId = "order-group")
    public void consumeSupplyRequests(RideRequest rideRequest)throws MessagingException {
        log.info("Consuming the message from topic {} for order {}", orderTopic, rideRequest.to());
    }

}
