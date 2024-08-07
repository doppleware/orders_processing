package com.acme.orders.order_shipping.domain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShippingRecordRepository extends JpaRepository<ShippingRecord, Integer> {
    ShippingRecord[] findByOrderUid(UUID shippingUid);

}