package com.acme.orders.order_shipping;
import com.acme.orders.order_contract.dto.ShipOrderMessage;
import com.acme.orders.order_shipping.domain.AddressRepository;
import com.acme.orders.order_shipping.domain.ShippingRecord;
import com.acme.orders.order_shipping.domain.ShippingRecordRepository;
import com.acme.orders.order_shipping.domain.TrackingRecordRepository;
import com.acme.orders.order_shipping.external_api.ApprovalApi;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.acme.orders.order_contract.common.HashLogic;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.UUID;

import static com.acme.orders.order_contract.config.Constants.SHIPPING_TOPIC;

@Component
public class OrderShipmentCoordinator {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ShippingRecordRepository shippingRecordRepository;

    @Autowired
    private TrackingRecordRepository trackingRecordRepository;

    @Autowired
    ApprovalApi approvalApi;

    SecureRandom random = new SecureRandom();
    @KafkaListener(topics = SHIPPING_TOPIC)
    @Transactional
    public void processMessage(ShipOrderMessage order) {

        System.out.println("shipping running");
        UUID orderUid = UUID.fromString(order.getKey());
        var existingRecords = shippingRecordRepository.findByOrderUid(orderUid);
        var billingEntities = new ArrayList< String >();

        ShippingRecord record;
        var randomShippingAuth= HashLogic.hashStrongRandom(random);

        //A record already exists
        if (existingRecords.length>0){
            record=existingRecords[0];
        }
        else{
            record = new ShippingRecord();
            record.setOrderUid(orderUid);
            shippingRecordRepository.save(record);
        }


    }
}
