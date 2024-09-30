package com.acme.orders.contract_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ORDER_CONTRACT")
public class OrderContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "orderContract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_id")
    private Shipping shipping;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private UUID orderUid;
    private String status;

    public void setUuid(UUID orderUid) {
        this.orderUid=orderUid;
    }
}