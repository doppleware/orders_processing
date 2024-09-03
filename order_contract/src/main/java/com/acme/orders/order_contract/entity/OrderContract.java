package com.acme.orders.order_contract.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "ORDER_CONTRACT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @Embedded
    private Shipping shipping;

    @Embedded
    private Payment payment;

    private String status;

}
