package com.cdq.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
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
public class RedisConfig  extends CachingConfigurerSupport {

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

    /**
     * 创建连接工厂
     *
     * @param jedisPoolConfig
     * @return
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().usePooling().poolConfig(jedisPoolConfig).and().readTimeout(Duration.ofMillis(2000)).build();
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(2);
        redisStandaloneConfiguration.setPort(6379);
        redisStandaloneConfiguration.setHostName("localhost");
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(redisConnectionFactory);

        //定义key序列化方式
        //RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long类型会出现异常信息;需要我们上面的自定义key生成策略，一般没必要
        //定义value的序列化方式
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();

        return template;
    }

}
