package com.henry.repository;

import com.henry.model.Order;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author: henry
 * @Date: 2021-08-20
 */
public interface OrderRepository extends ElasticsearchRepository<Order, Integer> {
}
