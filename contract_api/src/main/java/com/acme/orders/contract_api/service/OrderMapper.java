package com.acme.orders.contract_api.service;

import com.acme.orders.contract_api.dto.*;
import com.acme.orders.contract_api.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderMapper {
    public OrderContract toOrder(OrderRequest orderRequest, UUID orderUid) {
        OrderContract order = new OrderContract();
        order.setCustomer(toCustomer(orderRequest.getCustomer()));
        order.setItems(toItems(orderRequest.getItems()));
        order.setShipping(toShipping(orderRequest.getShipping()));
        order.setUuid(orderUid);
        order.setPayment(toPayment(orderRequest.getPayment()));
        order.setStatus(orderRequest.getStatus());
        return order;
    }

    private Customer toCustomer(Customer customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        return customer;
    }

    private List<OrderItem> toItems(List<OrderItem> itemDtos) {
        return itemDtos.stream()
                .map(dto -> {
                    OrderItem item = new OrderItem();
                    item.setProductName(dto.getProductName());
                    item.setQuantity(dto.getQuantity());
                    item.setPrice(dto.getPrice());
                    return item;
                })
                .collect(Collectors.toList());
    }

    private Shipping toShipping(Shipping shippingDto) {
        Shipping shipping = new Shipping();
        shipping.setAddress(shippingDto.getAddress());
        shipping.setCity(shippingDto.getCity());
        shipping.setPostalCode(shippingDto.getPostalCode());
        shipping.setCountry(shippingDto.getCountry());
        return shipping;
    }

    private Payment toPayment(Payment paymentDto) {
        Payment payment = new Payment();
        payment.setMethod(paymentDto.getMethod());
        payment.setTransactionId(paymentDto.getTransactionId());
        payment.setAmount(paymentDto.getAmount());
        return payment;
    }
}
