package com.Harevich.rideservice.kafka.producer;

import com.Harevich.rideservice.dto.request.DriverQueueRequest;
import com.Harevich.rideservice.dto.request.RideRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, RideRequest> kafkaTemplate;

    @Value("${spring.kafka.topic.order-queue}")
    private String topic;

    public void sendOrderRequest(DriverQueueRequest driverQueueRequest){
        log.info("Sending order via KAFKA");
        Message<DriverQueueRequest> message = MessageBuilder
                .withPayload(driverQueueRequest)
                .setHeader(KafkaHeaders.TOPIC,topic)
                .build();
        kafkaTemplate.send(message);
    }

}
