package com.henry.controller;

import com.henry.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: Henry
 * @Date: 2021/09/19 上午12:14
 * @Description:  redis实现购物车
 * Hash数据类型：值为多组映射，相当于JAVA中的Map。适合存储对象数据类型。
 * 因为用户ID作为唯一的身份标识，所以可以把模块名称+用户ID作为Redis的键；
 * 商品ID作为商品的唯一标识，可以把店铺编号+商品ID作为Hash元素的键，商品数量为元素的值。
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Resource
    private ShoppingCartService shoppingCartService;

    /**
     * http://localhost:8099/shoppingCart/addCommodity?userId=001&shopId=1234560&commodityId=001&commodityNum=336
     * 添加商品
     * @return
     * @param: userId 用户ID
     * @param: [{shopId:商铺id,commodityId:商品id,commodityNum:商品数量},{shopId:商铺id,commodityId:商品id,commodityNum:商品数量}]
     * 测试数据：
    [
    {
    "shopId": 123,
    "commodityId": 145350,
    "commodityNum": 1
    },
    {
    "shopId": 123,
    "commodityId": 6754434,
    "commodityNum": 9
    },
    {
    "shopId": 123,
    "commodityId": 7452,
    "commodityNum": 24
    },
    {
    "shopId": 3210,
    "commodityId": 98766,
    "commodityNum": 23
    },
    {
    "shopId": 456,
    "commodityId": 2435640,
    "commodityNum": 11
    }
    ]
     */
    @PostMapping("/addCommodity")
    public Map<String, Object> addCommodity(
            @RequestParam("userId") String userId,
            @RequestBody List<Map<String, Object>> list
    ) {
        Map<String, Object> map = shoppingCartService.addCommodity(userId, list);
        return map;
    }

    /**
     * 购物车列表
     * http://localhost:8080/shoppingCart/shoppingCartList?userId=001
     *
     * @param userId
     * @return 返回{店铺ID:商品ID=商品数量}的map。
     */
    @GetMapping("/shoppingCartList")
    public Map<Object, Object> shoppingCartList(
            @RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "pageNo", defaultValue = "0") Long pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize
    ) {
        Map<Object, Object> map = shoppingCartService.shoppingCartList(userId, pageNo, pageSize);
        return map;
    }

    /**
     * http://localhost:8080/shoppingCart/updateNum?userId=001&shopId=01&commodityId=123456&commodityNum=342
     * 修改商品数量。
     *
     * @param userId       用户id
     * @param shopId       商铺id
     * @param commodityId  商品id
     * @param commodityNum 商品数量
     * @return
     */
    @GetMapping("/updateNum")
    public Map<String, Object> updateNum(
            @RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "shopId", required = true) Long shopId,
            @RequestParam(value = "commodityId", required = true) Long commodityId,
            @RequestParam(value = "commodityNum", required = true) Double commodityNum
    ) {
        return shoppingCartService.updateNum(userId, shopId, commodityId, commodityNum);
    }

    /**
     * http://localhost:8080/shoppingCart/delCommodity?userId=001&shopId=01&commodityId=123457
     * 删除购物车中的商品
     * @return
     * @param: userId 用户id
     * @param: [{shopId:商铺id,commodityId:商品id},{shopId:商铺id,commodityId:商品id}]
     */
    @PostMapping("/delCommodity")
    public Map<String, Object> delCommodity(
            @RequestParam(value = "userId", required = true) String userId,
            @RequestBody List<Map<String, Object>> list
    ) {
        return shoppingCartService.delCommodity(userId, list);
    }

    public static void main(String[] args) {
        ShoppingCartController javaTest = new ShoppingCartController();
        System.out.println("结果是：" + javaTest.div(9, 0));
    }
    public int div(int a, int b) {
        try {
            return a / b;
        } catch (ArithmeticException e) {
            System.out.print("ArithmeticException ");
        } catch (NullPointerException e) {
            System.out.print("NullPointerException ");
        } catch (Exception e) {
            System.out.print("Exception ");
        } finally {
            System.out.print("finally ");
        }
        return 0;
    }

}
