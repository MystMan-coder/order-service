package com.dhananjaykr.orderservice.service;

import com.dhananjaykr.orderservice.dto.OrderRequestDTO;
import com.dhananjaykr.orderservice.dto.OrderResponseDTO;

public interface OrderService {

    OrderResponseDTO createOrder(OrderRequestDTO request);

    OrderResponseDTO getOrder(Long id);

    OrderResponseDTO updateOrder(Long id, OrderRequestDTO request);

    void deleteOrder(Long id);
}
