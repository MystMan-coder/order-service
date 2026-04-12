package com.dhananjaykr.orderservice.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Service
public class OrderConsumer {

//    @KafkaListener(topics = "order-created", groupId = "order-group")
//    public void consume(String message,
//                        @Header("kafka_receivedPartitionId") int partition) {
//
//        System.out.println("📥 Received: " + message + " from partition: " + partition);
//    }

    @KafkaListener(topics = "order-created", groupId = "order-group")
    public void consume(ConsumerRecord<String, String> record) {

        System.out.println("📥 Message: " + record.value());
        System.out.println("📍 Partition: " + record.partition());
    }
}