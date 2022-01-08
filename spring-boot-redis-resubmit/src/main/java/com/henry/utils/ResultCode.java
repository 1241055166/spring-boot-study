package com.henry.utils;

/**
 * @Author: Henry
 * @Date: 2021/10/17 下午9:37
 * @Description: 自定义响应码
 */
public class ResultCode {

    // 成功状态码
    public static final int SUCCESS = 20000;
    public static final int ERROR = 20001;

    // 参数错误

    public static final int PARAMS_IS_NULL = 10001;// 参数为空
    public static final int PARAMS_NOT_COMPLETE = 10002; // 参数不全
    public static final int PARAMS_TYPE_ERROR = 1003; // 参数类型错误
    public static final int PARAMS_IS_INVALID = 10004; // 参数无效
    // 用户错误
    public static final int USER_NOT_EXIST = 20001; // 用户不存在
    public static final int USER_NOT_LOGGED_IN = 20002; // 用户未登陆
    public static final int USER_ACCOUNT_ERROR = 20003; // 用户名或密码错误
    public static final int USER_ACCOUNT_FORBIDDEN = 20004; // 用户账户已被禁用
    public static final int USER_HAS_EXIST = 20005;// 用户已存在
    // 业务错误
    public static final int BUSINESS_ERROR = 30001;// 系统业务出现问题
    // 系统错误
    public static final int SYSTEM_INNER_ERROR = 40001; // 系统内部错误
   // 数据错误
    public static final int DATA_NOT_FOUND = 50001; // 数据未找到
    public static final int DATA_IS_WRONG = 50002;// 数据有误
    public static final int DATA_ALREADY_EXISTED = 50003;// 数据已存在

}
