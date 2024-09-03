package com.acme.orders.order_contract.service;

import com.acme.orders.order_contract.dto.OrderRequest;
import com.acme.orders.order_contract.dto.ShipOrderMessage;
import com.acme.orders.order_contract.dto.UpdateOrderRequest;
import com.acme.orders.order_contract.entity.OrderContract;
import com.acme.orders.order_contract.repository.OrderContractRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.acme.orders.order_contract.config.Constants.SHIPPING_TOPIC;

@Service
public class OrderContractService {
    @Autowired
    private KafkaTemplate<String, ShipOrderMessage> kafkaTemplate;

    @Autowired
    private OrderContractRepository repository;

    @Autowired
    private OrderMapper orderMapper;

    public void sendShipOrderMessage(ShipOrderMessage message) {
        kafkaTemplate.send(SHIPPING_TOPIC, message);
    }

    public List<OrderContract> getAllContracts() {
        return repository.findAll();
    }

    public Optional<OrderContract> getContractById(Long id) {
        return repository.findById(id);
    }

    public OrderContract updateContract(Long id, UpdateOrderRequest updateRequest) {
        var existingContract = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contract not found for ID: " + id));

        existingContract.setItems(
                updateRequest.getItems() != null && !updateRequest.getItems().isEmpty()
                        ? updateRequest.getItems()
                        : existingContract.getItems()
        );

        if (updateRequest.getShipping() != null) {
            existingContract.setShipping(updateRequest.getShipping());
        }

        if (updateRequest.getPayment() != null) {
            existingContract.setPayment(updateRequest.getPayment());
        }

        if (updateRequest.getStatus() != null && !updateRequest.getStatus().isBlank()) {
            existingContract.setStatus(updateRequest.getStatus());
        }
        return repository.save(existingContract);
    }



    public void deleteContract(Long id) {
        repository.deleteById(id);
    }

    public Long createOrderContract(OrderRequest orderRequest) {
        OrderContract orderContract = repository.save(orderMapper.toOrder(orderRequest));
        UUID orderUid = UUID.randomUUID();

        String shippingInfo = orderRequest.getShipping().getAddress();
        String key = "order-" + orderUid;

        ShipOrderMessage shipOrderMessage = new ShipOrderMessage();
        shipOrderMessage.setOrderUid(orderUid);
        shipOrderMessage.setShippingInfo(shippingInfo);
        shipOrderMessage.setKey(key);

        // Send the message to Kafka
        kafkaTemplate.send(SHIPPING_TOPIC, shipOrderMessage.getKey(), shipOrderMessage);
        return orderContract.getId();
    }
}
