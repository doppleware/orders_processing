package com.acme.orders.contract_api.config;

import com.acme.orders.order_contract.config.Constants;
import com.acme.orders.order_contract.dto.OrderStartedMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, OrderStartedMessage> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, OrderStartedMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(OrderStartedMessage message) {
        kafkaTemplate.send(Constants.ORDER_CONTRACT_STARTED_TOPIC, message);
    }
}
