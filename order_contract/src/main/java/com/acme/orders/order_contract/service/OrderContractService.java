package com.acme.orders.order_contract.service;

import com.acme.orders.order_contract.config.Constants;
import com.acme.orders.order_contract.dto.OrderRequest;
import com.acme.orders.order_contract.dto.ShipOrderMessage;
import com.acme.orders.order_contract.entity.OrderContract;
import com.acme.orders.order_contract.repository.OrderContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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

    public void createOrderContract(OrderRequest orderRequest) {
        OrderContract orderContract = repository.save(orderMapper.toOrder(orderRequest));
        // Generate a unique order ID
        UUID orderUid = UUID.randomUUID();

        // Extract and map data from OrderRequest to ShipOrderMessage
        String shippingInfo = orderRequest.getShipping().getAddress();
        String key = "order-" + orderUid;

        // Create and populate ShipOrderMessage
        ShipOrderMessage shipOrderMessage = new ShipOrderMessage();
        shipOrderMessage.setOrderUid(orderUid);
        shipOrderMessage.setShippingInfo(shippingInfo);
        shipOrderMessage.setKey(key);

        // Send the message to Kafka
        kafkaTemplate.send(SHIPPING_TOPIC, shipOrderMessage.getKey(), shipOrderMessage);
    }
}
