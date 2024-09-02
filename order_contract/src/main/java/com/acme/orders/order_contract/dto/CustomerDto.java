package com.acme.orders.order_contract.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private String name;
    private String email;
    private String phoneNumber;
}
