package com.acme.orders.order_contract.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Shipping {
    private String address;
    private String city;
    private String postalCode;
    private String country;
}
