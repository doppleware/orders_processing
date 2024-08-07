package com.acme.orders.order_contract.dto;

import java.io.Serializable;
import java.util.UUID;

public class ShipOrderMessage implements Serializable {
    private UUID orderUid;
    private String shippingInfo;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;
    // Getters and setters
    public UUID getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(UUID orderUid) {
        this.orderUid = orderUid;
    }

    public String getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(String shippingInfo) {
        this.shippingInfo = shippingInfo;
    }
}
