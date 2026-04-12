package com.dhananjaykr.orderservice.service.impl;

import com.dhananjaykr.orderservice.dto.OrderRequestDTO;
import com.dhananjaykr.orderservice.dto.OrderResponseDTO;
import com.dhananjaykr.orderservice.entity.Order;
import com.dhananjaykr.orderservice.exception.ResourceNotFoundException;
import com.dhananjaykr.orderservice.kafka.OrderProducer;
import com.dhananjaykr.orderservice.rabbitmq.OrderRabbitProducer;
import com.dhananjaykr.orderservice.repository.OrderRepository;
import com.dhananjaykr.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    private final OrderRabbitProducer orderRabbitProducer;

    @Override
    @CachePut(value = "orders", key = "#result.id")
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        System.out.println("🔥 DB HIT!!!");
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setTotalAmount(request.getTotalAmount());
        order.setStatus("CREATED");

        Order saved = orderRepository.save(order);

        // Kafka
        orderProducer.sendOrderEvent(
                String.valueOf(saved.getId()),
                "Order Created with ID: " + saved.getId()
        );

// RabbitMQ (NEW)
        orderRabbitProducer.sendMessage(
                "Order Notification for ID: " + saved.getId()
        );

        return new OrderResponseDTO(
                saved.getId(),
                saved.getCustomerId(),
                saved.getStatus(),
                saved.getTotalAmount()
        );
    }

    @Override
    @Cacheable(value = "orders", key = "#id")
    public OrderResponseDTO getOrder(Long id) {

        System.out.println("🔥 DB HIT!!!");

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return new OrderResponseDTO(
                order.getId(),
                order.getCustomerId(),
                order.getStatus(),
                order.getTotalAmount()
        );
    }

        @Override
        @CachePut(value = "orders", key = "#id")
        public OrderResponseDTO updateOrder(Long id, OrderRequestDTO req) {

            System.out.println("🔥 DB HIT!!!");

        Order order = orderRepository.findById(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Order not found")
                    );
            order.setCustomerId(req.getCustomerId());
            order.setStatus("UPDATED");
            order.setTotalAmount(req.getTotalAmount());

            Order savedOrder = orderRepository.save(order);

            return new OrderResponseDTO(
                    savedOrder.getId(),
                    savedOrder.getCustomerId(),
                    savedOrder.getStatus(),
                    savedOrder.getTotalAmount()
            );

        }

        @Override
        @CacheEvict(value = "orders", key = "#id")
        public void deleteOrder(Long id) {
         Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

         orderRepository.delete(order);
        }


    }

