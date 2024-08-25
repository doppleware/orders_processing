package com.acme.orders.order_contract.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_contracts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contractName;
    private String details;

}
