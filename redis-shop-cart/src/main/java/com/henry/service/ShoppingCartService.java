package com.henry.service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Henry
 * @Date: 2021/09/19 上午12:09
 * @Description:
 */
public interface ShoppingCartService {

    //购物车列表
    public Map<Object, Object> shoppingCartList(String userId,Long pageNo,Long pageSize);
    Map<String,Object> updateNum(String userId,Long shopId, Long commodityId, Double commodityNum);
    Map<String, Object> delCommodity(String userId, List<Map<String , Object>> list);
    Map<String, Object> addCommodity(String userId, List<Map<String , Object>> list);

}
