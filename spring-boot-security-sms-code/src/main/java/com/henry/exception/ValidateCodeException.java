package com.henry.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Description: 自定义异常
 *
 * @author henry
 * @date 2021/10/14 下午12:24
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
