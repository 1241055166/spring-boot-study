package com.henry.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 名称
     */
    private String userName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 地址
     */
    private String address;
}

