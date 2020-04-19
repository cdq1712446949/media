package com.cdq.util;

import com.cdq.dto.RedisCache;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/19 16:30
 * @description：注解工具类
 * @modified By：
 * @version: 1.0.1
 */
public class AnnotationUtil {

    public static String redisCacheEnable(Object object){

        Class clazz = object.getClass();

        if (clazz.isAnnotationPresent(RedisCache.class)){
            RedisCache redisCache = (RedisCache) clazz.getAnnotation(RedisCache.class);
            String value = redisCache.value();
            System.out.println(value);
        }

        return "";
    }


}
