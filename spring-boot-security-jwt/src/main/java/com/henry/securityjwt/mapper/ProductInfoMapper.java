package com.henry.securityjwt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.henry.securityjwt.entity.ProductInfo;

import java.util.List;

public interface ProductInfoMapper extends BaseMapper<ProductInfo> {

    List<ProductInfo> getProductInfoList();
}
