package com.henry.shiro.exception;

import com.henry.shiro.common.AjaxResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author bee
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public AjaxResult businessException(BusinessException businessException) {
        return AjaxResult.error(businessException.getStatus().getCode()
                , businessException.getStatus().getMessage());
    }

}
