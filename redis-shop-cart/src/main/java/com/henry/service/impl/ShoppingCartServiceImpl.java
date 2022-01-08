package com.henry.service.impl;

import com.henry.service.ShoppingCartService;
import com.henry.utils.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Henry
 * @Date: 2021/09/19 上午12:10
 * @Description: 购物车实现类
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private RedisService redisService;
    //购物车键名前缀
    public static final String SHOPPING_CART = "shoppingCart:";
    //购物车的元素键名前缀
    public static final String SHOP_ID = "shopId";

    //添加商品
    @Override
    public Map<String, Object> addCommodity(String userId, List<Map<String, Object>> list) {
        //记录：list中有哪些商品在购物车中已经存在。
        List<String> existCommoditys = new ArrayList<>();
        //todo 购物车key
        String key = SHOPPING_CART + userId;
        for (int i = 0; i < list.size(); i++) {
            String commodityKey = SHOP_ID + list.get(i).get("shopId") + ":" + list.get(i).get("commodityId");
            //todo 添加商品
            boolean boo = redisService.hsetnx(key, commodityKey, Double.parseDouble(list.get(i).get("commodityNum") + ""));
            if (!boo) {
                existCommoditys.add(commodityKey);
            }
        }
        Map<String, Object> m = new HashMap<String, Object>() {
            {
                put("existCommoditys", existCommoditys);
                put("existCommoditysMsg", "这些商品在购物车中已经存在，重复数量："+existCommoditys.size()+"。");
            }
        };
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", m);
        map.put("code", 0);
        return map;
    }

    //购物车列表
    @Override
    public Map<Object, Object> shoppingCartList(String userId, Long pageNo, Long pageSize) {
        //返回{店铺ID:商品ID=商品数量}的map。
        Map<Object, Object> map = redisService.hmget(SHOPPING_CART + userId);
        return map;
    }

    //修改商品数量
    @Override
    public Map<String, Object> updateNum(String userId, Long shopId, Long commodityId, Double commodityNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        //todo 购物车key
        String key = SHOPPING_CART + userId;
        //todo 商品key
        String commodityKey = SHOP_ID + shopId + ":" + commodityId;
        //修改购物车的数量
        boolean boo = redisService.hset(key, commodityKey, commodityNum);
        Map<String, Object> m = new HashMap<String, Object>() {
            {
                put("key", key);
                put("commodityKey", commodityKey);
                put("commodityNum", commodityNum);
            }
        };
        map.put("data", m);
        map.put("msg", boo == true ? "修改购物车商品数量成功。" : "修改购物车商品数量失败。");
        map.put("code", boo == true ? 0 : 1);
        return map;
    }

    //删除商品
    @Override
    public Map<String, Object> delCommodity(String userId, List<Map<String, Object>> list) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] commodityIds = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            //todo 商品key
            commodityIds[i] = SHOP_ID + list.get(i).get("shopId") + ":" + list.get(i).get("commodityId");
        }
        //todo 购物车key
        String key = SHOPPING_CART + userId;
        //删除商品的数量
        Long num = redisService.hdel(key, commodityIds);
        map.put("msg", "删除购物车的商品数量：" + num);
        map.put("code", 0);
        return map;
    }

}
