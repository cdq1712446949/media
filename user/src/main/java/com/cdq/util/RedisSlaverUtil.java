package com.cdq.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/18 15:10
 * @description：从redis服务器工具类，只能进行读操作
 * @modified By：
 * @version: 1.0.1
 */
@Component
public class RedisSlaverUtil {

    @Autowired
    private RedisTemplate slaveRedisTemplate;

    public Object get (String key){
        return slaveRedisTemplate.opsForValue().get(key);
    }

}
