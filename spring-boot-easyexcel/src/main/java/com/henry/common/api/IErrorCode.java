package com.henry.common.api;

/**
 * 封装API的错误码
 * Created by henry on 2021/12/20.
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
