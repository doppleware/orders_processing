package com.acme.orders.order_processing.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "billing_records")
public class BillingRecord extends BaseEntity{


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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id")
    private BillingEntity entity;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public BillingEntity getEntity() {
        return entity;
    }
}
