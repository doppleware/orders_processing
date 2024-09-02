package com.acme.orders.order_contract.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private String productName;
    private int quantity;
    private double price;
}
