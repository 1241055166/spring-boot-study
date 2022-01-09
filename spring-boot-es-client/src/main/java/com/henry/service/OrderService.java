package com.henry.service;


import com.henry.dto.PageResponse;
import com.henry.model.Order;

import java.util.List;

/**
 * @Author: henry
 * @Date: 2021-08-31
 */
public interface OrderService {
    void saveAll(List<Order> orders);

    Order findById(Integer id);

    void deleteById(Integer id);

    void updateById(Order order);

    PageResponse<Order> findList(Order order, Integer pageIndex, Integer pageSize);

    PageResponse<Order> findAll(Integer pageIndex, Integer pageSize);

    PageResponse<Order> findHighlight(Order order, Integer pageIndex, Integer pageSize);

    PageResponse<Order> findScroll(Order order, Integer pageIndex, Integer pageSize, String indexName, String scrollId);

    void bulkUpdate(List<Order> orders);

    List<String> suggest(String keyword);
}
