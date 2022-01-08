package com.henry.dao;

import com.henry.elasticsearch.document.EsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Description: 搜索系统中的商品管理自定义Dao
 * @Date: 2020/12/20
 */
public interface EsProductDao {
    List<EsProduct> getAllEsProductList(@Param("id") Long id);
}
