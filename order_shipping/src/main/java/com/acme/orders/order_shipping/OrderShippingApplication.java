package com.acme.orders.order_shipping;

import com.acme.orders.order_contract.config.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class OrderShippingApplication {



	public static void main(String[] args) {
		SpringApplication.run(OrderShippingApplication.class, args);
	}

	@Bean
	public NewTopic topic() {
		return TopicBuilder.name(Constants.SHIPPING_TOPIC)
				.partitions(10)
				.replicas(1)
				.build();
	}
}
