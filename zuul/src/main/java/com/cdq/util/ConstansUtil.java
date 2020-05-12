package com.cdq.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/7 13:54
 * @description：存放公共常量
 * @modified By：
 * @version: 1.0.1
 */
public class ConstansUtil {

    public static final String HEAD_KEY = "media_header";
    public static String TOKEN = "token";
    public static String EMPTY = "";

    public static void main(String[] args) {
        Map<String,Object> modelMap = new HashMap<>();
        modelMap.put("success",false);
        modelMap.put("errMsg","系统错误");
        System.out.println(modelMap.toString().getBytes());
        System.out.println("系统错误".getBytes());
    }

}
