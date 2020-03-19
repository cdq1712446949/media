package com.cdq.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/18 15:10
 * @description：Redis工具类
 * @modified By：
 * @version: 1.0.1
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public void set(String key , Object value){
        redisTemplate.opsForValue().set(key,value);
    };

    public Object get (String key){
        return redisTemplate.opsForValue().get(key);
    }

}
