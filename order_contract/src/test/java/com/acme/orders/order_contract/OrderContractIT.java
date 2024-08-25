package com.acme.orders.order_contract;

import com.acme.orders.order_contract.entity.OrderContract;
import com.acme.orders.order_contract.service.OrderContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderContractIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderContractService service;

    @BeforeEach
    public void setUp() {
        // Clear database to ensure isolation
        service.deleteAllContracts();

        // Initialize data before each test
        OrderContract contract1 = new OrderContract();
        contract1.setContractName("Contract 1");
        contract1.setDetails("Description 1");
        service.createContract(contract1);

        OrderContract contract2 = new OrderContract();
        contract2.setContractName("Contract 2");
        contract2.setDetails("Description 2");
        service.createContract(contract2);
    }

    @Test
    public void shouldGetAllContracts() throws Exception {
        mockMvc.perform(get("/api/contracts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].contractName").value(org.hamcrest.Matchers.containsInAnyOrder("Contract 1", "Contract 2")));
    }

    @Test
    public void shouldGetContractById() throws Exception {
        // Assuming the first contract has ID 1
        mockMvc.perform(get("/api/contracts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractName").value("Contract 1"));
    }

    @Test
    public void shouldCreateContract() throws Exception {
        OrderContract newContract = new OrderContract();
        newContract.setContractName("Contract 3");
        newContract.setDetails("Description 3");

        mockMvc.perform(post("/api/contracts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contractName\": \"Contract 3\", \"details\": \"Description 3\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractName").value("Contract 3"));
    }

    @Test
    public void shouldUpdateContract() throws Exception {
        OrderContract contract1 = new OrderContract();
        contract1.setContractName("Contract 1");
        contract1.setDetails("Description 1");
        OrderContract contract = service.createContract(contract1);

        mockMvc.perform(put("/api/contracts/" + contract.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contractName\": \"Updated Contract 1\", \"details\": \"Updated Description 1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractName").value("Updated Contract 1"));
    }

    @Test
    public void shouldDeleteContract() throws Exception {
        mockMvc.perform(delete("/api/contracts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify the contract is deleted
        mockMvc.perform(get("/api/contracts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
