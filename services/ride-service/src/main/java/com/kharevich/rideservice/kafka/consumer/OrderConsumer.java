package com.kharevich.rideservice.kafka.consumer;

import com.kharevich.rideservice.dto.request.QueueProceedRequest;
import com.kharevich.rideservice.dto.request.RideRequest;
import com.kharevich.rideservice.service.impl.RideServiceImpl;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.TraceFlags;
import io.opentelemetry.context.Context;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import io.opentelemetry.api.trace.TraceState;

import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {

    private final RideServiceImpl rideService;

    @Value("${spring.kafka.topic.order-queue}")
    private String orderTopic;

    @Autowired
    private Tracer tracer;

    @Autowired
    private ObservationRegistry observationRegistry;

    private final KafkaTemplate<String, RideRequest> kafkaTemplate;

    @KafkaListener(topics = "order-topic",groupId = "order-group")
    public void consumeSupplyRequests(
            QueueProceedRequest queueProceedRequest,
            @Header(name = "traceparent", required = false) String traceparent
    ) throws MessagingException {
                    log.info("OrderConsumer.Processing message");
                    rideService.tryToCreatePairFromQueue();
    }

}
