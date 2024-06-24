package com.acme.orders.order_processing.domain;

import org.springframework.data.repository.Repository;

public interface OrdersRepository extends Repository<OrderRecord, Integer> {

    void save(OrderRecord orderRecord);

    OrderRecord[] findByName(String name);

}
