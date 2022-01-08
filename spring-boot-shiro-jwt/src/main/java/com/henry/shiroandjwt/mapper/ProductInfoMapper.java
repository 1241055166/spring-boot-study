package com.henry.shiroandjwt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.henry.shiroandjwt.entity.ProductInfo;

import java.util.List;

public interface ProductInfoMapper extends BaseMapper<ProductInfo> {

    List<ProductInfo> getProductInfoList();
}
