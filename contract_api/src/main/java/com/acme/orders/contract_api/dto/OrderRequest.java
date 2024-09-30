package com.acme.orders.contract_api.dto;

import com.acme.orders.contract_api.entity.Customer;
import com.acme.orders.contract_api.entity.OrderItem;
import com.acme.orders.contract_api.entity.Payment;
import com.acme.orders.contract_api.entity.Shipping;
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
    private String orderName;

}