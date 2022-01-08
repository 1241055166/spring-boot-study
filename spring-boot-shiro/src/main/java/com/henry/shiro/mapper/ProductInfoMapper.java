package com.henry.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.henry.shiro.entity.ProductInfo;

import java.util.List;

public interface ProductInfoMapper extends BaseMapper<ProductInfo> {

    List<ProductInfo> getProductInfoList();
}
