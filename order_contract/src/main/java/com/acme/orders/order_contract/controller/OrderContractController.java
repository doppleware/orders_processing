package com.acme.orders.order_contract.controller;

import com.acme.orders.order_contract.entity.OrderContract;
import com.acme.orders.order_contract.service.OrderContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class OrderContractController {
    @Autowired
    private OrderContractService service;

    @GetMapping
    public List<OrderContract> getAllContracts() {
        return service.getAllContracts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderContract> getContractById(@PathVariable Long id) {
        return service.getContractById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public OrderContract createContract(@RequestBody OrderContract contract) {
        return service.createContract(contract);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderContract> updateContract(
            @PathVariable Long id, @RequestBody OrderContract contractDetails) {
        return ResponseEntity.ok(service.updateContract(id, contractDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        service.deleteContract(id);
        return ResponseEntity.noContent().build();
    }
}
