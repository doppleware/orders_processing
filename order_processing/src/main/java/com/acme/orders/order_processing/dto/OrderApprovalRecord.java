package com.acme.orders.order_processing.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderApprovalRecord {
    private boolean approved;
    private String orderId   ;
    private String comment;

    public boolean isApproved() {
        return approved;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getComment() {
        return comment;
    }

    public OrderApprovalRecord(){

    }

    public OrderApprovalRecord(boolean approved, String orderId, String comment){
        this.approved=approved;
        this.orderId=orderId;
        this.comment=comment;
    }


}
