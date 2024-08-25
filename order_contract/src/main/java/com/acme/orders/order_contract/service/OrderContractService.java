package com.acme.orders.order_contract.service;

import com.acme.orders.order_contract.entity.OrderContract;
import com.acme.orders.order_contract.repository.OrderContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderContractService {

    @Autowired
    private OrderContractRepository orderContractRepository;

    public List<OrderContract> getAllContracts() {
        return orderContractRepository.findAll();
    }

    public Optional<OrderContract> getContractById(Long id) {
        return orderContractRepository.findById(id);
    }

    public OrderContract createContract(OrderContract contract) {
        return orderContractRepository.save(contract);
    }

    public OrderContract updateContract(Long id, OrderContract contractDetails) {
        OrderContract contract = orderContractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));
        contract.setContractName(contractDetails.getContractName());
        contract.setDetails(contractDetails.getDetails());
        return orderContractRepository.save(contract);
    }

    public void deleteContract(Long id) {
        orderContractRepository.deleteById(id);
    }

    public void deleteAllContracts() {
        orderContractRepository.deleteAll();
    }

}
