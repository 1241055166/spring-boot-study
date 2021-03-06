package com.henry.properties;

import lombok.Data;

/**
 * @Description: 抽出共部分
 *
 * @author henry
 * @date 2021/10/13 上午10:43
 */
@Data
public class CodeProperties {

    /**
     * 验证码长度
     */
    private int length = 6;
    /**
     * 验证码过期时间
     */
    private int expireIn = 60;

    /**
     * 需要验证码的url字符串，用英文逗号隔开
     */
    private String url;
}
