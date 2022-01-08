package com.henry.securityjwt.controller;

import com.henry.securityjwt.common.AjaxResult;
import com.henry.securityjwt.entity.ProductInfo;
import com.henry.securityjwt.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: ProductInfoController
 * @Description: 商品类
 * @Date: 2020/9/27
 */
@RestController
@RequestMapping("/productInfo")
public class ProductInfoController {

    @Autowired
    private ProductInfoService productInfoService;

    @RequestMapping("/getProductList")
    public AjaxResult getProductList(){
        List<ProductInfo> list = productInfoService.getProductInfoList();
        return AjaxResult.success(list);
    }

}
