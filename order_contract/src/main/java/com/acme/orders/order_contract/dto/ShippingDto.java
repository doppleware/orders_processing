package com.acme.orders.order_contract.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingDto {
    private String address;
    private String city;
    private String postalCode;
    private String country;
}
