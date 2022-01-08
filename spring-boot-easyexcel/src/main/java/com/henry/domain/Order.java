package com.henry.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 订单
 * Created by henry on 2021/12/20.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Order {
    private Long id;
    private String orderSn;
    private Date createTime;
    private String receiverAddress;
    private Member member;
    private List<Product> productList;
}
