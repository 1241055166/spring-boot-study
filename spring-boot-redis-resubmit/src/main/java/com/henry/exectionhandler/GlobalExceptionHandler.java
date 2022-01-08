package com.henry.exectionhandler;

import com.henry.utils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Henry
 * @Date: 2021/10/17 下午9:34
 * @Description: 统一异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error();
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public R error(BaseException e){
        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode());
    }
}

