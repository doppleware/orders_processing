package com.acme.orders.order_contract.controller;

import com.acme.orders.order_contract.dto.OrderRequest;
import com.acme.orders.order_contract.dto.UpdateOrderRequest;
import com.acme.orders.order_contract.entity.OrderContract;
import com.acme.orders.order_contract.service.OrderContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders-contracts")
public class OrderContractController {
    private static final Logger logger = LoggerFactory.getLogger(OrderContractController.class);

    @Autowired
    private OrderContractService service;

    @GetMapping("/{id}")
    public ResponseEntity<OrderContract> getOrderContractById(@PathVariable Long id) {
        logger.info("Fetching order contract with id: {}", id);
        Optional<OrderContract> orderContract = service.getContractById(id);

        if (orderContract.isPresent()) {
            logger.info("Order contract found: {}", orderContract.get());
            return ResponseEntity.ok(orderContract.get());
        } else {
            logger.warn("Order contract with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderContract> updateOrderContract(
            @PathVariable Long id, @RequestBody UpdateOrderRequest orderRequest) {
        logger.info("Updating order contract with id: {} with data: {}", id, orderRequest);
        try {
            OrderContract updatedOrderContract = service.updateContract(id, orderRequest);
            logger.info("Order contract updated successfully: {}", updatedOrderContract);
            return ResponseEntity.ok(updatedOrderContract);
        } catch (Exception e) {
            logger.error("Error updating order contract with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(500).build(); // Internal Server Error
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        logger.info("Deleting order contract with id: {}", id);
        try {
            service.deleteContract(id);
            logger.info("Order contract with id {} deleted successfully", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting order contract with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(500).build(); // Internal Server Error
        }
    }

    @PostMapping("/process")
    @CrossOrigin(origins = "http://localhost:63342")
    public ResponseEntity<String> createOrderContract(@RequestBody OrderRequest orderRequest) {
        logger.info("Received request to create order contract: {}", orderRequest);
        try {
            Long orderId = service.createOrderContract(orderRequest);
            logger.info("Order contract created successfully with ID: {}", orderId);
            return ResponseEntity.ok("Order contract created successfully with Id: " + orderId);
        } catch (Exception e) {
            logger.error("Error processing order contract: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing order contract: " + e.getMessage());
        }
    }
}
