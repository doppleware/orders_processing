package com.acme.orders.order_contract.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Payment {
    private String method;
    private String transactionId;
    private double amount;
}
