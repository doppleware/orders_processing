package com.acme.orders.order_shipping.domain;

import jakarta.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    // getters and setters
}
