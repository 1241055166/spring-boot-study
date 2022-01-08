package com.henrt.ik;

import java.util.Random;

/**
 * @Description:   随机生成几位数的方法
 * @Author: huangjw-b
 * @Date: 2021/12/14
 */
public class RamdonNumberUtil {


    public static String runNumber() {
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb=new StringBuilder(6);
        for(int i=0;i<6;i++)
        {
            char ch=str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        System.out.println(sb.toString());
        String code = sb.toString();
        return code;
    }

    public static void main(String[] args) {
        runNumber();
    }


}
