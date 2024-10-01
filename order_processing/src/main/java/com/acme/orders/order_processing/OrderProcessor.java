package com.acme.orders.order_processing;
import com.acme.orders.order_contract.config.Constants;
import com.acme.orders.order_contract.dto.OrderStartedMessage;
import com.acme.orders.order_contract.dto.ShipOrderMessage;
import com.acme.orders.order_processing.domain.OrderRecord;
import com.acme.orders.order_processing.domain.OrdersRepository;
import com.acme.orders.order_processing.dto.OrderApprovalRecord;
import com.acme.orders.order_processing.external_api.ApprovalApi;
import com.acme.orders.order_processing.model.OrderInTransit;
import com.acme.orders.order_processing.security.HashProcessorBean;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static com.acme.orders.order_contract.config.Constants.ORDER_CONTRACT_STARTED_TOPIC;


@Component
public class OrderProcessor {

    @Autowired
    private OrdersRepository ordersRepo;

    @Autowired
    private OrderInTransitRepository inTransitRepo;

    @Autowired
    private HashProcessorBean hasProcessor;

    @Autowired
    ApprovalApi approvalApi;

    @Autowired
    private KafkaTemplate<String, ShipOrderMessage> kafkaTemplate;

    @KafkaListener(topics = ORDER_CONTRACT_STARTED_TOPIC)
    @Transactional
    public void processMessage(OrderStartedMessage order) {

        Pageable pageable = PageRequest.of(0, 100);
        String hashValue = hasProcessor.processHash(order.getKey());
        ArrayList<String> billingEntities = retrieveOrdersInfo(order);
        System.out.println(order.toString());
        OrderInTransit orderIT = new OrderInTransit(order.getKey(),"",hashValue,"","","", false);
        String[] fields = {"id", "orderTitle"};
        org.springframework.data.domain.Page<OrderInTransit> results = inTransitRepo.searchSimilar(orderIT,fields,pageable);
        for (OrderInTransit result: results){
            if (result.getId()==order.getKey()){
                orderIT.setApproved(true);

            }
        }
        try {
            OrderApprovalRecord approval = approvalApi.getApproval(order.getKey());
            if (approval!=null && approval.isApproved())
            {
                orderIT.setApproved(true);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        inTransitRepo.save(orderIT);
        for (String entity : billingEntities){
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setName(entity);
            ordersRepo.save(orderRecord);
        }


        ShipOrderMessage shipOrderMessage = new ShipOrderMessage();
        shipOrderMessage.setOrderUid(UUID.randomUUID());
        shipOrderMessage.setKey(UUID.randomUUID().toString());
        shipOrderMessage.setShippingInfo("");
        kafkaTemplate.send(Constants.SHIPPING_TOPIC, shipOrderMessage);

    }

    @WithSpan
    private ArrayList<String> retrieveOrdersInfo(OrderStartedMessage order) {
        OrderRecord[] orders = ordersRepo.findByName(order.getOrderName());
        orders = Arrays.copyOfRange(orders, 0, Math.min(orders.length, 30));

        ArrayList<String> billingEntities = new ArrayList< String >();

        for (OrderRecord orderInstance: orders){
            var billingRecords = orderInstance.getBillingRecords();

            for (var billingRecord : billingRecords){
                billingEntities.add(billingRecord.getEntity().getName());
                System.out.println(billingRecord.getEntity().getName());
            }
        }
        return billingEntities;
    }
}
