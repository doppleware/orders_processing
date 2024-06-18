package com.acme.orders.order_processing;
import com.acme.orders.order_processing.model.OrderInTransit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OrderInTransitRepository extends ElasticsearchRepository<OrderInTransit, String> {
}
