package com.dhananjaykr.orderservice.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO implements Serializable {
    private Long id;
    private Long customerId;
    private String status;
    private Double totalAmount;
}