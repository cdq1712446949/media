package com.cdq.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/18 9:28
 * @description：整合Redis配置类
 * @modified By：
 * @version: 1.0.1
 */
@Configuration
public class RedisConfig {

    /**
     * 配置连接池信息
     *
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(1500);
        //最大空闲连接数
        jedisPoolConfig.setMaxIdle(1500);
        //最小空闲连接数
        jedisPoolConfig.setMinIdle(500);
        //获取连接时最大等待时间
        jedisPoolConfig.setMaxWaitMillis(20000);
        //获取连接时检查是否可用
        jedisPoolConfig.setTestOnBorrow(true);
        //返回连接时检查是否可用
        jedisPoolConfig.setTestOnReturn(true);
        //是否开启空闲资源监测
        jedisPoolConfig.setTestWhileIdle(true);
        //-1不检测   单位为毫秒  空闲资源监测周期
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(300000);
        //资源池中资源最小空闲时间 单位为毫秒  达到此值后空闲资源将被移除
        jedisPoolConfig.setMinEvictableIdleTimeMillis(30 * 60 * 1000);
        //做空闲监测时，每次采集的样本数  -1代表对所有连接做监测
        jedisPoolConfig.setNumTestsPerEvictionRun(300);
        return jedisPoolConfig;
    }



}
