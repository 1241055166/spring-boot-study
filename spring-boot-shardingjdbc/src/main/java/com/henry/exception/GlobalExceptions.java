package com.henry.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName：GlobalExceptiions
 * @Description：全局异常
 * @Author：henry
 * @Date：2021/11/15 上午11:44
 * @Versiion：1.0
 */
@ControllerAdvice
public class GlobalExceptions {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handle(Exception ex) {
        logger.error("「 全局异常 」 ===============》{}", ex);
        return "「 全局异常 」错误信息:"+ex.getMessage();
    }
}
