package com.acme.orders.order_contract.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private String method;
    private String transactionId;
    private double amount;
}
