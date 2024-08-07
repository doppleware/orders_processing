package com.acme.orders.order_shipping.domain;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "shipping_records")
public class ShippingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private UUID orderUid;
    private Integer orderId;
    private String deliveryStatus;
    private String carrier;
    private Timestamp shippedAt;
    private Timestamp deliveryEstimate;

    @OneToMany(mappedBy = "shippingRecord")
    private Set<TrackingRecord> trackingRecords;

    public UUID getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(UUID shippingUid) {
        this.orderUid = shippingUid;
    }

    // getters and setters
}
