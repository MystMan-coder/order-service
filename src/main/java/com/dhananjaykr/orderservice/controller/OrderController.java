package com.dhananjaykr.orderservice.controller;

import com.dhananjaykr.orderservice.dto.OrderRequestDTO;
import com.dhananjaykr.orderservice.dto.OrderResponseDTO;
import com.dhananjaykr.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderRequestDTO request) {
        OrderResponseDTO created =  orderService.createOrder(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> get(@PathVariable Long id) {
       OrderResponseDTO order =  orderService.getOrder(id);
       return ResponseEntity.ok(order);
    }

    @PutMapping("{id}")
    public ResponseEntity<OrderResponseDTO> update(@PathVariable Long id, @RequestBody OrderRequestDTO request) {
        OrderResponseDTO updated =  orderService.updateOrder(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "Order deleted successfully";
    }
}
