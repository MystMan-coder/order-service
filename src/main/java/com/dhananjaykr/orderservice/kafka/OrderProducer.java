package com.dhananjaykr.orderservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "order-created";

    public void sendOrderEvent(String key, String message) {
        kafkaTemplate.send(TOPIC,key, message);
        System.out.println("Order/Message sent to Kafka: " + message);
    }
}
