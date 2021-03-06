package com.henry.properties.img;

import com.henry.properties.CodeProperties;
import lombok.Data;

/**
 * @Description: 图形验证码的默认配置
 *
 * @author henry
 * @date 2021/10/17 上午10:44
 */
@Data
public class ImageCodeProperties extends CodeProperties {

    public ImageCodeProperties() {

        setLength(4);
        setUrl("/login");
    }

    /**
     * 验证码图片宽度
     */
    private int width = 67;
    /**
     * 验证码图片高度
     */
    private int height = 23;
}
