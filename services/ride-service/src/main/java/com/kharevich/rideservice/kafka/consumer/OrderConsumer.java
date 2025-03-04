package com.kharevich.rideservice.kafka.consumer;

import com.kharevich.rideservice.dto.request.QueueProceedRequest;
import com.kharevich.rideservice.dto.request.RideRequest;
import com.kharevich.rideservice.service.impl.RideServiceImpl;
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

    @Value("${spring.kafka.topic.order-queue}")
    private String orderTopic;

    private final KafkaTemplate<String, RideRequest> kafkaTemplate;

    @KafkaListener(topics = "order-topic",groupId = "order-group")
    public void consumeSupplyRequests(QueueProceedRequest queueProceedRequest) throws MessagingException {
        log.info("OrderConsumer.Consuming the message from topic {} for entity {}", orderTopic, queueProceedRequest.entityId());
        rideService.tryToCreatePairFromQueue();
    }

}
