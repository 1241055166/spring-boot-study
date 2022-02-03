package com.henry.service;

import com.henry.properties.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;


/**
  * @Description: 抽出一个类
  *
  * @author henry
  * @date 2021/10/14 下午11:58
  */
public interface ValidateCodeGeneratorService {

    /**
     * 生成图片验证码
     *
     * @param request 请求
     * @return ImageCode实例对象
     */
    ImageCode generate(ServletWebRequest request);
}
