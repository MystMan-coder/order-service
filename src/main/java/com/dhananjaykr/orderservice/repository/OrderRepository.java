package com.dhananjaykr.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dhananjaykr.orderservice.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
