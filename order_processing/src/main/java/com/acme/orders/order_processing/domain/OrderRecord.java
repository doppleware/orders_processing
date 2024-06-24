package com.acme.orders.order_processing.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderRecord extends BaseEntity{

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public List<BillingRecord> getBillingRecords() {
        return billingRecords;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    @OrderBy("name")
    private List<BillingRecord> billingRecords = new ArrayList<>();
}




