package com.acme.orders.order_processing;

import com.acme.orders.order_processing.dto.IncomingOrder;
import com.acme.orders.order_processing.external_api.ApprovalApi;
import com.acme.orders.order_processing.model.OrderInTransit;
import com.acme.orders.order_processing.security.HashProcessorBean;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OrderProcessor {

    @Autowired
    private OrderInTransitRepository repository;

    @Autowired
    private HashProcessorBean hasProcessor;

    @Autowired
    ApprovalApi approvalApi;

    @KafkaListener(topics = "incomingOrders")
    public void processMessage(IncomingOrder order) {

        Pageable pageable = PageRequest.of(0, 100);
        String hashValue = hasProcessor.processHash(order.getValue());
        System.out.println(order.toString());
        var orderIT = new OrderInTransit(order.getKey(),"",hashValue,"","","", false);
        String[] fields = {"id", "orderTitle"};
        var results = repository.searchSimilar(orderIT,fields,pageable);

        try {
            var approval = approvalApi.getApproval(order.getKey());
            if (approval.isApproved())
            {
                orderIT.setApproved(true);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        repository.save(orderIT);
    }
}
