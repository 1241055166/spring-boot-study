package com.henry.mongodbDemo.service;


import com.henry.mongodbDemo.pojo.Logistics;
import com.henry.mongodbDemo.pojo.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private static Logger logger= LoggerFactory.getLogger(OrderService.class);

    @Autowired
    MongoTemplate mongoTemplate;

    //创建订单，传来order对象
    public void addorder(Order order){
        mongoTemplate.insert(order,"order");
    }

    //更新物流
    public  void addLogisticsAndUpdateStatus(Logistics logistics){
        String status=logistics.getOperation();
        Query query = new Query(Criteria.where("_id").is(logistics.getOrderId()));
        Update update = new Update();
        update.set("status", status);//更新状态
        update.push("logistics",logistics);
        mongoTemplate.upsert(query, update, Order.class);
    }

    //通过id查询物流
    public Order getOrderById(int id){
        Query query = new Query(Criteria.where("_id").is(id));
        Order order=mongoTemplate.findOne(query, Order.class);
        return  order;
    }

    //根据id删除记录
    public boolean deleteOrderById(int id){
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query,Order.class,"order");
        return  true;
    }

    //查询所有订单
    public List<Order>getAllorder()
    {
        List<Order> list=mongoTemplate.findAll(Order.class,"order");
        return  list;
    }

}
