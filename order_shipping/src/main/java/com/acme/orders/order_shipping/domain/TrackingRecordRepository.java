package com.acme.orders.order_shipping.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingRecordRepository extends JpaRepository<TrackingRecord, Integer> {

}