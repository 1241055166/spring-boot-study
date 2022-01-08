package com.henry.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 商品
 * Created by henry on 2021/12/20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Product {
    private Long id;
    private String productSn;
    private String name;
    private String subTitle;
    private String brandName;
    private BigDecimal price;
    private Integer count;
}
