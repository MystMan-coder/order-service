package com.dhananjaykr.orderservice.service;

import com.dhananjaykr.orderservice.dto.OrderRequestDTO;
import com.dhananjaykr.orderservice.dto.OrderResponseDTO;
import com.dhananjaykr.orderservice.entity.Order;
import com.dhananjaykr.orderservice.exception.ResourceNotFoundException;
import com.dhananjaykr.orderservice.kafka.OrderProducer;
import com.dhananjaykr.orderservice.rabbitmq.OrderRabbitProducer;
import com.dhananjaykr.orderservice.repository.OrderRepository;
import com.dhananjaykr.orderservice.service.impl.OrderServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderProducer orderProducer;

    @Mock
    private OrderRabbitProducer orderRabbitProducer;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order(1L, 101L, "CREATED", 500.0);
    }

    // ✅ TEST: CREATE ORDER
    @Test
    void testCreateOrder() {

        OrderRequestDTO request = new OrderRequestDTO(101L, 500.0);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderResponseDTO response = orderService.createOrder(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("CREATED", response.getStatus());

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderProducer, times(1)).sendOrderEvent(anyString(), anyString());
        verify(orderRabbitProducer, times(1)).sendMessage(anyString());
    }

    // ✅ TEST: GET ORDER SUCCESS
    @Test
    void testGetOrderSuccess() {

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderResponseDTO response = orderService.getOrder(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());

        verify(orderRepository, times(1)).findById(1L);
    }

    // ❌ TEST: GET ORDER NOT FOUND
    @Test
    void testGetOrderNotFound() {

        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.getOrder(1L);
        });

        verify(orderRepository, times(1)).findById(1L);
    }

    // ✅ TEST: UPDATE ORDER
    @Test
    void testUpdateOrder() {

        OrderRequestDTO request = new OrderRequestDTO(202L, 800.0);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderResponseDTO response = orderService.updateOrder(1L, request);

        assertEquals("UPDATED", response.getStatus());
        assertEquals(800.0, response.getTotalAmount());

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    // ✅ TEST: DELETE ORDER
    @Test
    void testDeleteOrder() {

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).delete(order);
    }
}