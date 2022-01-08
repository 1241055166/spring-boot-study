package com.henry.shiro.service;


import com.henry.shiro.entity.ProductInfo;

import java.util.List;

/**
 * @ClassName: ProductInfoService
 * @Description: 商品service
 * @Date: 2020/9/27
 */
public interface ProductInfoService {

    List<ProductInfo> getProductInfoList();
}
