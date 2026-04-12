package com.dhananjaykr.orderservice.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderRabbitConsumer {

    @RabbitListener(queues = "order.notification.queue")
    public void receive(String message) {

        System.out.println("📥 Received from RabbitMQ: " + message);

        // simulate processing
        System.out.println("📧 Sending notification for: " + message);
    }
}
