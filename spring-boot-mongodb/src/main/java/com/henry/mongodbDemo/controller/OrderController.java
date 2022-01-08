package com.henry.mongodbDemo.controller;


import com.henry.mongodbDemo.pojo.Logistics;
import com.henry.mongodbDemo.pojo.Order;
import com.henry.mongodbDemo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    private static Logger logger= LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;

    /**
     * 添加订单
     * @param order
     * @return
     */
    @PostMapping("addorder")
    public String addorder(Order order){
        order.setStatus("发货中");
        order.setOrderTime(new Date());
        order.setShipTime(new Date());
        orderService.addorder(order);
        return "添加成功";
    }

    /**
     * 更新物流信息
     * @param logistics
     * @return
     */
    @PostMapping("updateorder")
    public String updateorder(Logistics logistics){
        logistics.setOperationTime(new Date());
        orderService.addLogisticsAndUpdateStatus(logistics);
        return "添加成功";
    }

    /**
     * 通过id查询物流
     * @param id
     * @return
     */
    @GetMapping("getorderbyid")
    public Order getOrderById(int id)
    {
        Order order=orderService.getOrderById(id);
        return  order;
    }

    /**
     *  删除订单
     * @param id
     * @return
     */
    @GetMapping("deletebyid")
    public String deleteById(int id)
    {
        orderService.deleteOrderById(id);
        return "成功";
    }

    /**
     * 查询所有订单
     * @return
     */
    @GetMapping("getallorders")
    public Map<String,Object> getAllOrder()
    {
        Map<String,Object> map=new HashMap<>();
        List<Order> list=orderService.getAllorder();
        map.put("code","0");
        map.put("count",list.size());
        map.put("data",list);
        return  map;
    }

}
