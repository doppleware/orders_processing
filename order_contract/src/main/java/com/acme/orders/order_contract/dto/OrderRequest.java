package com.acme.orders.order_contract.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private CustomerDto customer;
    private List<ItemDto> items;
    private ShippingDto shipping;
    private PaymentDto payment;
    private String status;
}
