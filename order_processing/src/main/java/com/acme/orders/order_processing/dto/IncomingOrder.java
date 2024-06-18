package com.acme.orders.order_processing.dto;

import java.io.Serializable;

public class IncomingOrder implements Serializable {
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;
    private String value;

    public IncomingOrder() {
    }

    public IncomingOrder(String key, String value) {
        this.key = key;
        this.setValue(value);
    }

    @Override
    public String toString() {
        return "IncomingOrder{" +
                "key='" + key + '\'' +
                ", value='" + getValue() + '\'' +
                '}';
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
