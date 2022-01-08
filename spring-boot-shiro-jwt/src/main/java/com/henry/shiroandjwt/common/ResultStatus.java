package com.henry.shiroandjwt.common;

/**
 * @author henry
 */
public interface ResultStatus {
    /**
     * 错误码
     */
    int getCode();

    /**
     * 错误信息
     */
    String getMessage();
}
