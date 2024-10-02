package com.acme.orders.contract_api.service;

import com.acme.orders.contract_api.dto.OrderRequest;
import com.acme.orders.contract_api.dto.UpdateOrderRequest;
import com.acme.orders.contract_api.entity.Customer;
import com.acme.orders.contract_api.entity.OrderContract;
import com.acme.orders.contract_api.repository.CustomerRepository;
import com.acme.orders.contract_api.repository.OrderContractRepository;
import com.acme.orders.order_contract.dto.OrderStartedMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.acme.orders.order_contract.config.Constants.ORDER_CONTRACT_STARTED_TOPIC;

@Service
public class OrderContractService {
    @Autowired
    private KafkaTemplate<String, OrderStartedMessage> kafkaTemplate;

    @Autowired
    private OrderContractRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderMapper orderMapper;

    public void sendShipOrderMessage(OrderStartedMessage message) {
        kafkaTemplate.send(ORDER_CONTRACT_STARTED_TOPIC, message);
    }

    public List<OrderContract> getAllContracts() {
        return orderRepository.findAll();
    }

    public Optional<OrderContract> getContractById(Long id) {
        return orderRepository.findById(id);
    }

    public OrderContract updateContract(Long id, UpdateOrderRequest updateRequest) {
        var existingContract = orderRepository.findById(id)
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
        return orderRepository.save(existingContract);
    }

    public void deleteContract(Long id) {
        orderRepository.deleteById(id);
    }

    public Long createOrderContract(OrderRequest orderRequest) {
        Customer existingCustomer = customerRepository.findByEmail(orderRequest.getCustomer().getEmail());
        if (existingCustomer != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email address already exists");
        }

        UUID orderUid = UUID.randomUUID();


        var order = orderMapper.toOrder(orderRequest, orderUid);
        var orderContract = orderRepository.save(order);

        String shippingInfo = orderRequest.getShipping().getAddress();
        String key = "order-" + orderUid;

        OrderStartedMessage orderStartedMessage = new OrderStartedMessage();
        orderStartedMessage.setOrderUid(orderUid);
        orderStartedMessage.setShippingInfo(shippingInfo);
        orderStartedMessage.setOrderName(orderRequest.getOrderName());
        orderStartedMessage.setKey(key);

        // Send the message to Kafka
        kafkaTemplate.send(ORDER_CONTRACT_STARTED_TOPIC, orderStartedMessage.getKey(), orderStartedMessage);
        return orderContract.getId();
    }
}
