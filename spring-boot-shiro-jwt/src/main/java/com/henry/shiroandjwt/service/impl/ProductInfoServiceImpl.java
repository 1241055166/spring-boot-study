package com.henry.shiroandjwt.service.impl;


import com.henry.shiroandjwt.entity.ProductInfo;
import com.henry.shiroandjwt.mapper.ProductInfoMapper;
import com.henry.shiroandjwt.service.ProductInfoService;
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
