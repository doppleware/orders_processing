package com.acme.orders.order_contract.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PURCHASE_ITEM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private int quantity;
    private double price;
}
