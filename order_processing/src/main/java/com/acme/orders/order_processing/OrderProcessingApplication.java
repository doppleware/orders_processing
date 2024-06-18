package com.acme.orders.order_processing;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class OrderProcessingApplication {



	public static void main(String[] args) {
		SpringApplication.run(OrderProcessingApplication.class, args);
	}

	@Bean
	public NewTopic topic() {
		return TopicBuilder.name("incomingOrders")
				.partitions(10)
				.replicas(1)
				.build();
	}
}
