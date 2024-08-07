package com.acme.orders.order_shipping.domain;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tracking_records")
public class TrackingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String trackingNumber;
    private String status;
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "shipping_record_id")
    private ShippingRecord shippingRecord;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    // getters and setters
}
