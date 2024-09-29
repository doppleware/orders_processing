package com.acme.orders.contract_api.repository;

import com.acme.orders.contract_api.entity.OrderContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderContractRepository extends JpaRepository<OrderContract, Long> {
}
