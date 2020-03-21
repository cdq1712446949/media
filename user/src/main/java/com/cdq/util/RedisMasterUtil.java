package com.cdq.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/18 15:10
 * @description：主Redis服务器工具类，能进行读写操作
 * @modified By：
 * @version: 1.0.1
 */
@Component
public class RedisMasterUtil {

    @Autowired
    private RedisTemplate masterRedisTemplate;

    public void set(String key , Object value){
        masterRedisTemplate.opsForValue().set(key,value);
    };

    public Object get (String key){
        return masterRedisTemplate.opsForValue().get(key);
    }

}
