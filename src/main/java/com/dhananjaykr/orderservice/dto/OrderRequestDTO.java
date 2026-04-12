package com.dhananjaykr.orderservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OrderRequestDTO {
    private Long customerId;
    private Double totalAmount;
}
