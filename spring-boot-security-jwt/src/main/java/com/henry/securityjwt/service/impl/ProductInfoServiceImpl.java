package com.henry.securityjwt.service.impl;


import com.henry.securityjwt.entity.ProductInfo;
import com.henry.securityjwt.mapper.ProductInfoMapper;
import com.henry.securityjwt.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ProductInfoServiceImpl
 * @Description: TODO
 * @Date: 2020/9/27
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getProductInfoList() {
        return productInfoMapper.getProductInfoList();
    }
}
