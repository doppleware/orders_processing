package com.acme.orders.order_contract.controller;

import com.acme.orders.order_contract.dto.OrderRequest;
import com.acme.orders.order_contract.service.OrderContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders-contracts")
public class OrderContractController {
    @Autowired
    private OrderContractService service;

    @PostMapping("/process")
    @CrossOrigin(origins = "http://localhost:63342")
    public ResponseEntity<String> createOrderContract(@RequestBody OrderRequest orderRequest) {
        try {
            service.createOrderContract(orderRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Order contract created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing order contract: " + e.getMessage());
        }
    }
}
