package com.henry.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Henry
 * @Date: 2021/10/18 下午10:12
 * @Description: 日期工具类
 */

public class DateUtils {
    // 日期转字符串，返回指定的格式
    public static String dateToString(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }
}
