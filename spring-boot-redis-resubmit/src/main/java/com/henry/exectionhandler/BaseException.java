package com.henry.exectionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Henry
 * @Date: 2021/10/17 下午9:33
 * @Description: 自定义异常
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseException extends RuntimeException {

    private Integer code;
    private String msg;

}

