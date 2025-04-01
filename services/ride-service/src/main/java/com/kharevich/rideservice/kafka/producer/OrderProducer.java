package com.kharevich.rideservice.kafka.producer;

import com.kharevich.rideservice.dto.request.QueueProceedRequest;
import com.kharevich.rideservice.dto.request.RideRequest;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private Tracer tracer;

    public void sendOrderRequest(QueueProceedRequest queueProceedRequest){
        log.info("OrderProducer.Sending order kafka request");
        Message<QueueProceedRequest> message = MessageBuilder
                .withPayload(queueProceedRequest)
                .setHeader(KafkaHeaders.TOPIC,topic)
                .setHeader("traceparent", tracer.currentSpan().context().traceId())
                .build();
        kafkaTemplate.send(message);
    }

}
