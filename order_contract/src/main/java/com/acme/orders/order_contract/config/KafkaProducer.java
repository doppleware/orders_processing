package com.acme.orders.order_contract.config;

import com.acme.orders.order_contract.dto.ShipOrderMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, ShipOrderMessage> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, ShipOrderMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ShipOrderMessage message) {
        kafkaTemplate.send(Constants.SHIPPING_TOPIC, message);
    }
}
