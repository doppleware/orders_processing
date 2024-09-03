package com.acme.orders.order_contract.repository;

import com.acme.orders.order_contract.entity.OrderContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderContractRepository extends JpaRepository<OrderContract, Long> {
}
