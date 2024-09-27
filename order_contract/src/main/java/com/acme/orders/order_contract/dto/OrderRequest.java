package com.acme.orders.order_contract.dto;

import com.acme.orders.order_contract.entity.Customer;
import com.acme.orders.order_contract.entity.OrderItem;
import com.acme.orders.order_contract.entity.Payment;
import com.acme.orders.order_contract.entity.Shipping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Customer customer;
    private List<OrderItem> items;
    private Shipping shipping;
    private Payment payment;
    private String status;
}
