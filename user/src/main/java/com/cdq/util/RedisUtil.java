package com.cdq.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/18 15:10
 * @description：从redis服务器工具类，只能进行读操作
 * @modified By：
 * @version: 1.0.1
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public Object get (String key){
        return redisTemplate.opsForValue().get(key);
    }
    public void set (String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }
    public void set (String key, Object value, long time , TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,time,timeUnit);
    }
    public boolean del (String key){
        return  redisTemplate.delete(key);
    }


}
