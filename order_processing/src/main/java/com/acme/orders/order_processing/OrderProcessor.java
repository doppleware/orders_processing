package com.acme.orders.order_processing;
import com.acme.orders.order_processing.domain.OrderRecord;
import com.acme.orders.order_processing.domain.OrdersRepository;
import com.acme.orders.order_processing.dto.IncomingOrder;
import com.acme.orders.order_processing.external_api.ApprovalApi;
import com.acme.orders.order_processing.model.OrderInTransit;
import com.acme.orders.order_processing.security.HashProcessorBean;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

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

    @KafkaListener(topics = "incomingOrders")
    @Transactional
    public void processMessage(IncomingOrder order) {

        Pageable pageable = PageRequest.of(0, 100);
        String hashValue = hasProcessor.processHash(order.getValue());
        var orders = ordersRepo.findByName("");
        var billingEntities = new ArrayList< String >();
        for (var orderInstance: orders){
            var billingRecords = orderInstance.getBillingRecords();

            for (var billingRecord : billingRecords){
                billingEntities.add(billingRecord.getEntity().getName());
                System.out.println(billingRecord.getEntity().getName());
            }
        }
        System.out.println(order.toString());
        var orderIT = new OrderInTransit(order.getKey(),"",hashValue,"","","", false);
        String[] fields = {"id", "orderTitle"};
        var results = inTransitRepo.searchSimilar(orderIT,fields,pageable);
        for (var result: results){
            if (result.getId()==order.getKey()){
                orderIT.setApproved(true);

            }
        }
        try {
            var approval = approvalApi.getApproval(order.getKey());
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
        ordersRepo.save(new OrderRecord());

    }
}
